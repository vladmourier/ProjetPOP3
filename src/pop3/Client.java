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
import static java.lang.Integer.parseInt;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Thibaud
 */
public class Client extends ObjetConnecte{
    
    static Socket sServer, socket;
    String dataServer;
    InetAddress ia;
    int port_dest;
    
    String etatCourant;
    public static final String ETAT_INITIALISATION = "initialisation";
    public static final String ETAT_APOPENVOYE = "apop envoye";
    public static final String ETAT_TRANSACTION = "transaction";
    public static final String ETAT_SUPPRESSION = "suppression";
    public static final String ETAT_ATTENTEQUIT = "attente du quit";
    
    
    public Client(String ip) throws SocketException {
        super();
        try {
            port_dest = 110;
            ia = InetAddress.getByName(ip);
            sServer = new Socket(ia, port_dest);
            System.out.println("YAY!");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Client(InetAddress ia, int port) throws SocketException, IOException {
        super(ia, port);
        this.socket = new Socket(ia, port);
        this.port_c = this.socket.getLocalPort();
        System.out.println("Socket cree port : " + port_c);
        this.IS = this.socket.getInputStream();
        this.BIS = new BufferedInputStream(this.IS);
        this.OS = this.socket.getOutputStream();
        this.BOS = new BufferedOutputStream(OS);
    }
    
    public void changementEtat(String etat){
        etatCourant = etat;
        System.out.println(" ---- Etat courant: " + etatCourant + "\n");
    }
    
    public String lectureConsole(String action) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(action);
        String s = br.readLine();
        return s;
    }
    
    public void envoiMsg(String msg) throws IOException{
        String s = msg + "\r\n";
        this.BOS.write(s.getBytes());
        this.BOS.flush();
        this.BOS.flush();
        System.out.println("J'envoie : " + msg);
    }
    public static void main(String args[]) {
        try {
            // Etat initialisation----------------------------------------------------
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Client c = new Client(InetAddress.getByName("localhost"), 110);
            c.changementEtat(c.ETAT_INITIALISATION);
            String s = c.receive();
            System.out.println(s);
            if (!s.contains("+OK POP3 SERVER READY")){
                System.out.println("Authorisation refusee..\nFermeture de la session");//à gérer
                return;
            }
            //faire lecture de la console et envoi des crédentials
            String user = c.lectureConsole("Veuillez entrer votre nom d'utilisateur: ");
            String mdp = c.lectureConsole("Veuillez entrer votre mot de passe: ");
            c.envoiMsg("APOP " + user + " " + mdp);
            c.changementEtat(c.ETAT_APOPENVOYE);
            // Etat APOP envoyé ------------------------------------------------------
            s = c.receive();
            System.out.println(s);
            if (!s.contains("+OK")){
                System.out.println("Mauvais APOP..\nFermeture de la session");//à gérer
                return;
            }
            //Etat transaction -------------------------------------------------------
            c.changementEtat(c.ETAT_TRANSACTION);
            System.out.println(s);
            //gérer le dernier "s" pour récupérer le nombre de msgs
            int nbMsg = parseInt(s.split(" ")[3]);
            System.out.println("Vous avez " + nbMsg + " messages dans votre boite mail.");
            String transaction = c.lectureConsole("Que voulez vous faire?\n 1 - Lire les messages?\n 2 - Supprimer les messages?\n 0 - Quitter?");
            
            //gérer l'affichage des msg
            //gérer le quit
            //faire une fonction pour chaque états?
            //
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
