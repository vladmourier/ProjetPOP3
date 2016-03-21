/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package pop3;

import Etats.EtatInitialisation;
import Etats.EtatTransaction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.net.*;
import java.util.Scanner;
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
    public boolean quit;
    
    String etatCourant;
    public static final String ETAT_INITIALISATION = "initialisation";
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
        this.quit = false;
    }
    
    public void changementEtat(String etat){
        etatCourant = etat;
        System.out.println(" ---- Etat courant: " + etatCourant + "\n");
    }
    
    public String lectureConsole(String action) throws IOException{
        System.out.println(action);
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if(s.contains("quit") ||s.contains("Quit")){
            quit = true;
        }
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
//            // Etat initialisation----------------------------------------------------
            Client c = new Client(InetAddress.getByName("localhost"), 110);
            EtatInitialisation init = new EtatInitialisation(c);
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
