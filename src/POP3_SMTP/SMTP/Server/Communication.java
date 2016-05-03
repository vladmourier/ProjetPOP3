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

    private String sender;
    private String[] receivers;

    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        fileManager = new FileManager();
        currentState = ETAT_INITIALISATION;
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[256];
        String received = "";
        boolean quit_asked = false;
        while (!quit_asked) {
            try {
                this.sendMessage(220, "SMTP service ready");
                currentState = ETAT_ATTENTE_EHLO;

                BIS.read(buffer);
                received = new String(buffer);
                switch (currentState) {
                    case ETAT_INITIALISATION:
                        manageAttenteEhloState(received);
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

    public void manageAttenteEhloState(String received) {
        if (received.startsWith("EHLO")) {
            this.sendMessage(250, "OK");
            currentState = ETAT_ATTENTE_MAIL;
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            //TODO FERMER LE SERVEUR : FAIRE UNE METHODE POUR TOUT CLOSE PROPREMENT
        } else {
            this.sendMessage(503, "bad sequence of command");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        }
    }

    public void manageAttenteMailoState(String received) {
        if (received.startsWith("MAIL")) {
            sender = received.split("<")[1].split(">")[0];
            this.sendMessage(250, "sender ok");
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            //TODO FERMER LE SERVEUR : FAIRE UNE METHODE POUR TOUT CLOSE PROPREMENT
        } else if (received.startsWith("RSET")) {
            this.sendMessage(250, "Reseted");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        } else {
            this.sendMessage(503, "bad sequence of command");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        }
    }

    public void manageAttenteRcptState(String received) {
        if (received.startsWith("RCPT")) {
            String temp = received.split("<")[1].split(">")[0];
            if (fileManager.getUserNames().contains(temp)) {
                receivers[receivers.length] = temp;
                this.sendMessage(250, "receiver ok");
            } else {
                this.sendMessage(550, "no such user");
            }
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            //TODO FERMER LE SERVEUR : FAIRE UNE METHODE POUR TOUT CLOSE PROPREMENT
        } else if (received.startsWith("RSET")) {
            this.sendMessage(250, "Reseted");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        } else {
            this.sendMessage(503, "bad sequence of command");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        }
    }

    public void manageAttenteDataState(String received) {
        if (received.startsWith("RCPT")) {
            String temp = received.split("<")[1].split(">")[0];
            if (fileManager.getUserNames().contains(temp)) {
                receivers[receivers.length] = temp;
                this.sendMessage(250, "receiver ok");
            } else {
                this.sendMessage(550, "no such user");
            }
        } else if (received.startsWith("DATA")){
            this.sendMessage(354, "Enter mail, end with \".\" on a line by itself");
        }
        else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            //TODO FERMER LE SERVEUR : FAIRE UNE METHODE POUR TOUT CLOSE PROPREMENT
        } else if (received.startsWith("RSET")) {
            this.sendMessage(250, "Reseted");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        } else {
            this.sendMessage(503, "bad sequence of command");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
        }
    }

    public void manageAttenteContentState(String received) {
        //gestion du corps du mail = galère !!
        //quand on reçoit le point -> créer un nouvel objet mail, tout mettre dedans
        //puis appeler le filManager pour tout stocker dans les fichiers proprement
    }

    /**
     * Méthode d'envoi d'un message au client
     *
     * @param errorCode : code d'erreur d'entête du message
     * @param message : message descriptif ajouté
     */
    public void sendMessage(int errorCode, String message) {
        try {
            String mess = errorCode + " " + message + "\r\n";;
            this.BOS.write(mess.getBytes());
            this.BOS.flush();
            System.out.print("J'envoie : " + mess);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
