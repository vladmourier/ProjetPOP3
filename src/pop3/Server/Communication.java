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
        byte[] buffer = new byte[1024];
        currentState = "initialisation";
        String received;
        boolean quit_asked=false;
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            BIS.read(buffer);
            received = new String(buffer);
            System.out.println(received);
            POP3ServerMessage msg = new POP3ServerMessage(POP3ServerMessage.SERVER_READY, true);
            this.sendPop3ServerMessage(msg);
            currentState = ETAT_AUTORISATION;
            while(true && !quit_asked){
                switch(currentState){
                    case ETAT_AUTORISATION:
                        manageAuthorizationState(received, buffer);
                        break;
                    case ETAT_USER_RECU:
                        break;
                    case ETAT_TRANSACTION:
                        break;
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
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
    
    public boolean sendPop3ServerMessage(POP3ServerMessage m) throws IOException{
        this.BOS.write(m.getMessage().getBytes());
        System.out.println(m.getMessage());
        this.BOS.flush();
        return true;
    }
    
    public POP3ServerMessage retrieveUserMessages(){
        return null;
    }
    
    public POP3ServerMessage retrieveUserMessage(String userEmail, int i){
        return null;
    }
    
    private boolean UserCommandIsValid(String received) {
        // Vérifier la validité de la commande user reçue
        return true;
    }
    
    private boolean ApopCommandIsValid(String received) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void manageAuthorizationState(String received, byte[] buffer) throws IOException{
        BIS.read(buffer);
        received = new String(buffer);
        if(received.startsWith("USER")){
            if(UserCommandIsValid(received)){
                System.out.println(received);
                currentState = ETAT_USER_RECU;
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR USER COMMAND IS NOT VALID OR THE REQUESTED USER WAS NOT FOUND", false));
            }
        } else if (received.startsWith("APOP")){
            if(ApopCommandIsValid(received)){
                
            } else {
                sendPop3ServerMessage(new POP3ServerMessage("-ERR APOP COMMAND IS NOT VALID OR THE REQUESTED CREDENTIALS WERE NOT CORRECT", false));
            }
            
        } else if (received.startsWith("QUIT")){
        } else {
            sendPop3ServerMessage(new POP3ServerMessage("-ERR INVALID COMMAND", false));
        }
    }
}
