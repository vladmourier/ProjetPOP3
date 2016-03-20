/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientPackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Thibaud
 */
public class Client extends ObjetConnecte{
    
    static Socket sServer;
    String dataServer;
    InetAddress ia;
    int port_dest;
    
    String currentState;
    public static final String ETAT_AUTORISATION = "autorisation";
    public static final String ETAT_TRANSACTION = "transaction";
    public static final String ETAT_USER_RECU = "user recu";
    
    public Client(String ip) throws SocketException {
        super();
        try {
            port_dest = 1024 + 110;
            ia = InetAddress.getByName(ip);
            sServer = new Socket(ia, port_dest);
            System.out.println("YAY!");
            this.OS = sServer.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = sServer.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client(InetAddress ia, int port) throws SocketException, IOException {
        super(ia, port);
        this.sServer = new Socket(ia, port);
        this.port_c = this.sServer.getLocalPort();
        System.out.println("Socket cree port : " + port_c);
        this.IS = this.sServer.getInputStream();
        this.BIS = new BufferedInputStream(this.IS);
        this.OS = this.sServer.getOutputStream();
        this.BOS = new BufferedOutputStream(OS);
//        byte[] buffer = new byte[1024];
//        this.BIS.read(buffer);
//        String msg = new String(buffer);
//        System.out.println(msg);
        String msg = "test\r\n";
        System.out.println(msg);
        BOS.write(msg.getBytes());
        BOS.flush();
        System.out.println("prout");
    }


public void envoiMsg(String msg){
        try {
            this.BOS.write(msg.getBytes());
            this.BOS.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public String receiveMsg(){
    String msg = "";    
    byte[] buffer = new byte[256];
    try {
            this.BIS.read(buffer);
            msg = new String(buffer);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    return msg;
}


public String getMsgClient(){
        InputStream is = null;
        BufferedReader br = null;
        String line = null;
        try {
            is = System.in;
            br = new BufferedReader(new InputStreamReader(is));
            line = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
}

public static void main(String args[]) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Client c = new Client(InetAddress.getLocalHost(), 110);
            //Client c = new Client(InetAddress.getByName("134.214.116.110"), 110);
            String msg = c.receiveMsg();
            System.out.println("message recu: " + msg);
            System.out.println(c.ETAT_AUTORISATION);
            if (!msg.contains("+OK")){
                System.out.println("Erreur serveur");
                return;
            }
            System.out.println("Bienvenue, veuillez entrer votre nom d'utilisateur");
            String usr = c.getMsgClient();
            System.out.println("Veuillez entrer votre mot de passe:");
            String mdp = c.getMsgClient();
            c.envoiMsg("APOP " + usr + " " + mdp);
            System.out.println(c.ETAT_USER_RECU);
            
            
        
            
            
            /* try {//envoi du datagram de connection au serveur RX302
            System.out.println("Connection au server RX302 ... ");
            buffer = "Hello server RX302 !".getBytes("ascii");
            dpEnvoi = new DatagramPacket(buffer, buffer.length, ia, ObjetConnecte.port_c);
            dsClient.send(dpEnvoi);

            //Reception du message de bienvenue du server
            dsClient.receive(dpRecoit);
            dataServer = new String(dpRecoit.getData(), "ascii");
            port = dpEnvoi.getPort();
            System.out.println(dataServer + " - @IP : " + port);

            //Envoi pepere du datagram
            boolean run = true;
            System.out.println("Saisissez Close pour mettre fin Ã  la connexion");
            while (run) {
            buffer = new byte[ObjetConnecte.MAX];
            System.out.println("quel est le message Ã  envoyer?");
            Scanner sc = new Scanner(System.in);
            buffer = sc.nextLine().getBytes("ascii");
            String strCheck = new String(buffer, "ascii");
            dpEnvoi = new DatagramPacket(buffer, buffer.length, ia, port);
            if ("Close".equals(strCheck)) {
            dsClient.send(dpEnvoi);
            run = false;
            } else if(buffer.length==0){
            System.out.println("Message vide !");
            } else {
            dsClient.send(dpEnvoi);
            }
            }
            } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur envoi datagram depuis le client");
            }*/
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
