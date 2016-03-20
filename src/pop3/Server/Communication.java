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
    
    private String currentState;
    public static final String ETAT_AUTORISATION = "autorisation";
    public static final String ETAT_TRANSACTION = "transaction";
    public static final String ETAT_USER_RECU = "user recu";
    
    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }
    
    @Override
    public void run() {
        System.out.println("ACCEPT OK");
        byte[] buffer = new byte[256];
        currentState = "initialisation";
        String received = "";
        boolean quit_asked=false;
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            POP3ServerMessage msg = new POP3ServerMessage(POP3ServerMessage.SERVER_READY, true);
            this.sendPop3ServerMessage(msg);
            String s = this.receive();
            System.out.println("J'ai reçu : " + s);
            currentState = ETAT_AUTORISATION;
//            while(true && !quit_asked){
//                switch(currentState){
//                    case ETAT_AUTORISATION:
//                        quit_asked = manageAuthorizationState(received, buffer);
//                        break;
//                    case ETAT_USER_RECU:
//                        manageUserReceivedState(received, buffer);
//                        break;
//                    case ETAT_TRANSACTION:
//                        break;
//                }
//            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    public void sendPop3ServerMessage(POP3ServerMessage m) throws IOException{
        String s = m.getMessage() + "\r\n";
        this.BOS.write(s.getBytes());
        this.BOS.flush();
        this.BOS.flush();
        System.out.println("J'envoie : " + m.getMessage());
    }
    
    public POP3ServerMessage retrieveUserMessages(){
        return null;
        /**
         * TODO
         */
    }
    
    public POP3ServerMessage retrieveUserMessage(String userEmail, int i){
        return null;
        /**
         * TODO
         */
    }
    
    private boolean UserCommandIsValid(String received) {
        // Vérifier la validité de la commande user reçue
        /**
         * TODO
         */
        return true;
    }
    
    private boolean ApopCommandIsValid(String received) {
        /**
         * TODO
         */
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean RetrieveCommandIsValid(String s ){
        return false;
    }
    
    private boolean DeleteCommandIsValid(String s){
        return false;
    }
    
    private boolean manageTransactionState(String received, byte[] buffer) throws IOException{
        BIS.read(buffer);
        received = new String(buffer);
        if(received.startsWith("RETR")){
            if(RetrieveCommandIsValid(received)){
                
            } else {
                
            }
        } else if (received.startsWith("DELE")){
            if(DeleteCommandIsValid(received)){
                
            } else {
                
            }
        } else if (received.startsWith("QUIT")){
            
        } else {
            
        }
        return false;
    }
    
    private void manageUserReceivedState(String received, byte[] buffer) throws IOException{
        BIS.read(buffer);
        received = new String(buffer);
        System.out.println("J'ai reçu : " + received);
        if(received.startsWith("PASS")){
            currentState = ETAT_TRANSACTION;
            /**
             * TODO
             */
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
                /**
                 * TODO
                 */
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR USER COMMAND IS NOT VALID OR THE REQUESTED USER WAS NOT FOUND", false));
            }
        } else if (received.startsWith("APOP")){
            if(ApopCommandIsValid(received)){
                currentState = ETAT_TRANSACTION;
                /**
                 * TODO
                 */
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR APOP COMMAND IS NOT VALID OR THE REQUESTED CREDENTIALS WERE NOT CORRECT", false));
            }
            
        } else if (received.startsWith("QUIT")){
            /**
             * TODO
             */
            //sendPop3ServerMessage(new POP3ServerMessage());
            return true;
        } else {
            sendPop3ServerMessage(new POP3ServerMessage("-ERR INVALID COMMAND", false));
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
