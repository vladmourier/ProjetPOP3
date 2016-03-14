/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pop3;

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
public class Client {
    
    static DatagramSocket dsClient;
    DatagramPacket dpEnvoi;
    DatagramPacket dpRecoit;
    byte[] buffer;
    String dataServer;
    InetAddress ia;
    String etat;
    int port;
    public Client() {
        initSocket();
    }

private void initSocket() {
       try {
           //initiation de dpEnvoi
           ia = InetAddress.getByName("134.214.116.110");
           ObjetConnecte objConnect= new ObjetConnecte(ia, 1024+110);
           dsClient = new DatagramSocket(objConnect.getPort_c(),objConnect.getIa_c());         
           buffer = "hi!".getBytes();
           dpEnvoi = new DatagramPacket(buffer, buffer.length, objConnect.getIa_c(), objConnect.getPort_c());
       } catch (SocketException ex) {
               Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
               System.out.println("erreur DatagramSocket");
       } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public void envoiMsg(String msg){
        try {
            buffer = msg.getBytes();
            dpEnvoi = new DatagramPacket(buffer, buffer.length, ia, port);
            dsClient.send(this.dpEnvoi);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public String receiveMsg(){
        try {
            dsClient.receive(this.dpRecoit);
            buffer = this.dpRecoit.getData();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(buffer, StandardCharsets.UTF_8);
}

public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            Client client = new Client();
            client.initSocket();
            client.etat="initialisation";
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
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }   
}
