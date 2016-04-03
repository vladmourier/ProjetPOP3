/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pop3.Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pop3.Email;
import pop3.ObjetConnecte;

/**
 *
 * @author Vladimir
 */
public class Communication extends ObjetConnecte implements Runnable {
    
    public int port_dest;
    public int port_ecoute;
    public InetAddress address_dest;
    private Socket Sclient;
    private FileManager fileManager;
    private User currentUser;
    private String currentState;
    public static final String ETAT_AUTORISATION = "autorisation";
    public static final String ETAT_TRANSACTION = "transaction";
    public static final String ETAT_USER_RECU = "user recu";
    
    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        fileManager = new FileManager();
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }
    
    @Override
    public void run() {
        System.out.println("ACCEPT OK");
        byte[] buffer = new byte[256];
        String received = "";
        boolean quit_asked=false;
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            POP3ServerMessage msg = new POP3ServerMessage(POP3ServerMessage.SERVER_READY);
            this.sendPop3ServerMessage(msg);
            currentState = ETAT_AUTORISATION;
            while(true && !quit_asked){
                switch(currentState){
                    case ETAT_AUTORISATION:
                        quit_asked = manageAuthorizationState(received, buffer);
                        break;
                    case ETAT_USER_RECU:
                        manageUserReceivedState(currentUser.getId(),received, buffer);
                        break;
                    case ETAT_TRANSACTION:
                        quit_asked = manageTransactionState(received, buffer);
                        break;
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    public void sendPop3ServerMessage(POP3ServerMessage m) throws IOException{
        String s = m.getMessage() + "\r\n";
        this.BOS.write(s.getBytes());
        this.BOS.flush();
        System.out.println("J'envoie : " + m.getMessage());
    }
    
    public ArrayList<Email> retrieveUserMessages(){
        return fileManager.getMails(currentUser.getId());
    }
    
    public Email retrieveUserMessage(int i){
        return fileManager.getMails(currentUser.getId()).get(i-1);
    }
    
    public int getUserIdFromUsername(String username) throws IOException{
        return fileManager.findUser(username);
    }
    
    private boolean UserCommandIsValid(String received) {
        // Vérifier la validité de la commande user reçue
        String username;
        username = received.split(" ")[1];
        try {
            return fileManager.findUser(username) != 0;
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private boolean PassCommandIsValid(int userid, String received) {
        String pass = received.split(" ")[1];
        try {
            return   fileManager.verifyPass(userid, pass);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    private boolean ApopCommandIsValid(String received) {
        int userid;
        String user, pass, s;
        boolean okPass = false, okUser;
        s = received.substring(5);
        user = s.split(" ")[0];
        pass = s.split(" ")[1].split("\r\n")[0];
        okUser = UserCommandIsValid(received);
        try {
            userid = fileManager.findUser(user);
            if(userid==0) return false;
            okPass = fileManager.verifyPass(userid, pass);
            if(okPass && okUser){
                currentUser = new User(userid, user, pass);
            }
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return okPass && okUser;
    }
    
    private boolean RetrieveCommandIsValid(String s ){
        if(s.startsWith("RETR")){
            int nb = fileManager.getMails(currentUser.getId()).size();
            String sub = s.split(" ")[1];
            int target = Integer.parseInt(sub.split("\r\n")[0]);
            if(nb >= target){
                return true;
            }
        }
        return false;
    }
    
    private boolean DeleteCommandIsValid(String s){
        if(s.startsWith("DELE")){
            if(fileManager.getMails(currentUser.getId()).size() >= Integer.parseInt(s.split(" ")[1])){
                return true;
            }
        }
        return false;
    }
    
    private boolean manageTransactionState(String received, byte[] buffer) throws IOException{
        BIS.read(buffer);
        received = new String(buffer);
        if(received.startsWith("RETR")){
            if(RetrieveCommandIsValid(received)){
                System.out.println("OK");
                POP3ServerMessage p = new POP3ServerMessage();
                sendPop3ServerMessage(p.getMsgShowingEmail(retrieveUserMessage(Integer.parseInt(received.split(" ")[1].split("\r\n")[0]))));
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR THE MAILBOX DOES NOT CONTAIN THE REQUESTED MESSAGE"));
            }
        } else if (received.startsWith("DELE")){
            if(DeleteCommandIsValid(received)){
                fileManager.deleteMail(currentUser.getId(), Integer.parseInt(received.split(" ")[1]));
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR A PROBLEM OCCURED DURING DELETION"));
            }
        } else if (received.startsWith("QUIT")){
            /**
             * TODO : traiter les mails marqués comme supprimés
             *          Si il y a des erreurs, on envoie -ERR au client
             *          Sinon on envoie +OK Server Signing off
             */
            return true;
        } else {
            sendPop3ServerMessage(new POP3ServerMessage("-ERR INVAID COMMAND"));
        }
        return false;
    }
    
    private void manageUserReceivedState(int userid, String received, byte[] buffer) throws IOException{
        BIS.read(buffer);
        received = new String(buffer);
        System.out.println("J'ai reçu : " + received);
        if(received.startsWith("PASS") && PassCommandIsValid(userid,received)){
            currentState = ETAT_TRANSACTION;
            /**
             * TODO : setter l'user courant si ce n'est pas déjà fait
             */
            POP3ServerMessage m = new POP3ServerMessage();
            sendPop3ServerMessage(m.getMsgServerInitMailbox(fileManager.getMails(currentUser.getId()),(int) fileManager.getMailsSize(currentUser.getId())));
        } else {
            sendPop3ServerMessage(new POP3ServerMessage("-ERR PASSWORD IS INVALID"));
            currentState = ETAT_AUTORISATION;
        }
    }
    
    private boolean manageAuthorizationState(String received, byte[] buffer) throws IOException{
        System.out.println("Je lis");
        this.BIS.read(buffer);
        received = new String (buffer);
        System.out.println("J'ai reçu : " + received);
        if(received.startsWith("USER")){
            if(UserCommandIsValid(received)){
                System.out.println(received);
                currentState = ETAT_USER_RECU;
                sendPop3ServerMessage(new POP3ServerMessage(
                        POP3ServerMessage.SERVER_WAITING_FOR_PASS
                ));
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR USER COMMAND IS NOT VALID OR THE REQUESTED USER WAS NOT FOUND"));
            }
        } else if (received.startsWith("APOP")){
            if(ApopCommandIsValid(received)){
                currentState = ETAT_TRANSACTION;
                /**
                 * TODO : mettre modifier l'user courant
                 */
                POP3ServerMessage m = new POP3ServerMessage();
                sendPop3ServerMessage(m.getMsgServerInitMailbox(fileManager.getMails(currentUser == null ? fileManager.findUser(received.split(" ")[1]) : currentUser.getId()),(int) fileManager.getMailsSize(currentUser == null ? fileManager.findUser(received.split(" ")[1]) : currentUser.getId())));
                
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR APOP COMMAND IS NOT VALID OR THE REQUESTED CREDENTIALS WERE NOT CORRECT"));
            }
            
        } else if (received.startsWith("QUIT")){
            sendPop3ServerMessage(new POP3ServerMessage(
                    POP3ServerMessage.SERVER_SIGNING_OFF
            ));
            return true;
        } else {
            sendPop3ServerMessage(new POP3ServerMessage("-ERR INVALID COMMAND"));
        }
        return false;
    }
    
    public int getPort_dest() {
        return port_dest;
    }
    
    public void setPort_dest(int port_dest) {
        this.port_dest = port_dest;
    }
    
    public InetAddress getAddress_dest() {
        return address_dest;
    }
    
    public void setAddress_dest(InetAddress address_dest) {
        this.address_dest = address_dest;
    }
}
