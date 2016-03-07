/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Vladimir
 */
public class Communication extends ObjetConnecte implements Runnable {

    public int port_dest;
    public int port_ecoute;
    public InetAddress address_dest;
    private Socket Sclient;

    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        String racine = "web";
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            
            
        } catch (IOException ex) {

        }
    }

    public void response() throws SocketException {
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
        this.BOS.flush();
        return true;
    }
    
    public POP3ServerMessage retrieveUserMessages(){
        return null;
    }
    
    public POP3ServerMessage retrieveUserMessage(String userEmail, int i){
        return null;
    }
}
