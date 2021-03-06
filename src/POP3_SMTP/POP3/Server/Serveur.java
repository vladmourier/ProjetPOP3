/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package POP3_SMTP.POP3.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Vladimir
 */
public class Serveur{
    ServerSocket socketServer;
    
   public Serveur(int port_s) throws SocketException, IOException{
    //this.socketServer = new ServerSocket(port_s);
       this.socketServer = initSSLServerSocket(port_s);
   }
   public Serveur(){
       
   }
   
   public ServerSocket initSSLServerSocket(int port){
        try {
            SSLServerSocketFactory sslfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket ss = (SSLServerSocket) sslfactory.createServerSocket(port);
            String[] cipherSuites = ss.getSupportedCipherSuites();
            String[] parametres = new String[cipherSuites.length];
            int i = 0;
            for (String param: cipherSuites){
                if(param.contains("anon")){
                    parametres[i] = param;
                    i++;
                }
            }
            ss.setEnabledCipherSuites(cipherSuites);
            return ss;
        } catch (IOException ex) {
            Logger.getLogger(CommunicationSecured.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            return null;
        }
    }
   
   public void run(boolean secured) {
       while (true) {
           try {
                Socket Sclient = this.socketServer.accept();
                if(secured){
                    new Thread(new Communication(Sclient)).start();
                } else {
                    new Thread(new CommunicationSecured(Sclient)).start();
                }
                //this.socketServer.close();
            } catch (IOException ex) {
                Logger.getLogger(POP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
