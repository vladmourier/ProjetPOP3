/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Server;

import POP3_SMTP.FileManager;
import POP3_SMTP.ObjetConnecte;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class Communication extends ObjetConnecte implements Runnable {
    
    public int port_dest;
    public InetAddress address_dest;
    private final Socket Sclient;
    private FileManager fileManager;
    private String currentState;
    private static final String ETAT_INITIALISATION = "initialisation";
    private static final String ETAT_ATTENTE_EHLO = "attente ehlo";
    private static final String ETAT_ATTENTE_MAIL = "attente mail";
    private static final String ETAT_ATTENTE_RCPT = "attente rcpt";
    private static final String ETAT_ATTENTE_DATA = "attente data";
    private static final String ETAT_ATTENTE_CONTENT = "attente contenu";
    
    
    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        fileManager = new FileManager();
        currentState = ETAT_INITIALISATION;
        try{
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
        }catch(IOException exception){
            exception.printStackTrace();
        }
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }
    @Override
    public void run() {
        byte[] buffer = new byte[256];
        String received = "";
        boolean quit_asked=false;
        while(!quit_asked){
            try {
                BIS.read(buffer);
                received = new String(buffer);
                switch(currentState){
                    case ETAT_INITIALISATION:
                        manageInitialisationState(received);
                        break;
                    case ETAT_ATTENTE_EHLO:
                        manageAttenteEhloState(received);
                        break;
                    case ETAT_ATTENTE_MAIL:
                        manageAttenteMailoState(received);
                        break;
                    case ETAT_ATTENTE_RCPT:
                        manageAttenteRcptState(received);
                        break;
                    case ETAT_ATTENTE_DATA:
                        manageAttenteDataState(received);
                        break;
                    case ETAT_ATTENTE_CONTENT:
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void manageInitialisationState(String received){ }
    public void manageAttenteEhloState(String received){ }
    public void manageAttenteMailoState(String received){ }
    public void manageAttenteRcptState(String received){ }
    public void manageAttenteDataState(String received){ }
    public void manageAttenteContentState(String received){ }
    
}
