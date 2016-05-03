/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Server;

import POP3_SMTP.POP3.Server.POP3;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vlad
 */
public class Serveur {
    ServerSocket socketServer;
    
    public Serveur(int port_s) throws SocketException, IOException{
        //this.socketServer = new ServerSocket(port_s);
        this.socketServer = new ServerSocket(port_s);
    }
    
    public void run(boolean secured) {
        while (true) {
            try {
                Socket Sclient = this.socketServer.accept();
                new Thread(new POP3_SMTP.SMTP.Server.Communication(Sclient)).start();
            } catch (IOException ex) {
                Logger.getLogger(POP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
