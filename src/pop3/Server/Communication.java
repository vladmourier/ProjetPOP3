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
        String currentState = "";
        String received;
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            POP3ServerMessage msg = new POP3ServerMessage(POP3ServerMessage.SERVER_READY, true);
            this.sendPop3ServerMessage(msg);
            currentState = ETAT_AUTORISATION;
            switch(currentState){
                case ETAT_AUTORISATION:
                    BIS.read(buffer);
                    received = new String(buffer);
                    if(UserCommandIsValid(received)){
                        System.out.println(received);
                    }
                    break;
                case ETAT_USER_RECU:
                    break;
                case ETAT_TRANSACTION:
                    break;
            }
            
            
        } catch (IOException ex) {
            
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
}