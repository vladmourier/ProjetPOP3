/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
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


public void envoiMsg(String msg){
        try {
            this.BOS.write(msg.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public String receiveMsg(){
        byte[] buffer = new byte[1024];
        this.BIS.read(buffer);
        return new String(buffer);
}

public String msgClient(){
    return null;
}

public static void main(String args[]) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Client client = new Client("134.214.116.113");
            System.out.println("test");
            System.out.println(client.etat);
            String msg = client.receiveMsg();
            if (!msg.contains("+OK")){
            System.out.println("Erreur serveur");
            return;
            }
            System.out.println(msg);
            System.out.println("Bienvenue, veuillez entrer votre nom d'utilisateur");
            String entree = br.readLine();
            client.envoiMsg("USER "+entree);
            client.etat="user envoyé";
            */
            
        
            
            
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
        }
    }   
}
