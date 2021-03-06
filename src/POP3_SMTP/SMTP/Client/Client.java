/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Client;

import POP3_SMTP.ObjetConnecte;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import POP3_SMTP.POP3.Server.CommunicationSecured;

/**
 *
 * @author Thibaud
 */
public class Client extends ObjetConnecte {

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

    public Client(InetAddress ia, int port) throws SocketException, IOException {
        super(ia, port);
        this.socket = new Socket(ia, port);
//        this.socket = initSSLSocket(ia, port);
        this.port_c = this.socket.getLocalPort();
        System.out.println("Socket cree port : " + port_c);
        this.IS = this.socket.getInputStream();
        this.BIS = new BufferedInputStream(this.IS);
        this.OS = this.socket.getOutputStream();
        this.BOS = new BufferedOutputStream(OS);
        this.quit = false;
    }

    public Socket initSSLSocket(InetAddress addressServer, int port) {
        try {
            SSLSocketFactory sslfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket ss = (SSLSocket) sslfactory.createSocket(addressServer, port);
            String[] cipherSuites = ss.getSupportedCipherSuites();
            String[] parametres = new String[cipherSuites.length];
            int i = 0;
            for (String param : cipherSuites) {
                if (param.contains("anon")) {
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

    public void changementEtat(String etat) {
        etatCourant = etat;
        System.out.println(" ---- Etat courant: " + etatCourant + "\n");
    }

    public String lectureConsole(String action) throws IOException {
        System.out.println(action);
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if (s.contains("quit") || s.contains("Quit")) {
            quit = true;
        }
        return s;
    }

    public String receiveMail() {
        byte[] buffer = new byte[1];
        String s = "";
        int ok;
        try {
            ok = BIS.read(buffer);
            while (ok != -1) {
                s += new String(buffer);
                if (s.contains("+OK") && s.endsWith("\r\n.\r\n")) {
                    ok = -1;
                } else if (s.contains("-ERR") && s.endsWith("\r\n")) {
                    ok = -1;
                } else {
                    ok = BIS.read(buffer);
                }
            }
            System.out.println(s);

        } catch (IOException ex) {
            Logger.getLogger(ObjetConnecte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return s;
    }

    public String receiveResponse() {
        byte[] buffer = new byte[1];
        String s = "";
        int ok;
        try {
            ok = BIS.read(buffer);
            while (ok != -1) {
                s += new String(buffer);
                /* if (s.contains("+OK") && s.endsWith("\r\n.\r\n")) { ok = -1;
                 * } else if (s.contains("-ERR") && s.endsWith("\r\n")) { ok =
                 * -1; } else { */
                if (s.endsWith("\r\n")) {
                    ok = -1;
                } else {
                    ok = BIS.read(buffer);
                }
                //}
            }
            System.out.println(s);

        } catch (IOException ex) {
            Logger.getLogger(ObjetConnecte.class.getName()).log(Level.SEVERE, null, ex);
        }

        return s;
    }

    public void envoiMsg(String msg) throws IOException {
        String s = msg + "\r\n";
        this.BOS.write(s.getBytes());
        this.BOS.flush();
        //this.BOS.flush();
        System.out.println("J'envoie : " + msg);
    }
}
