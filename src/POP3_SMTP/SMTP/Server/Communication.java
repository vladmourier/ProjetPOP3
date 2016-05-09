/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Server;

import POP3_SMTP.Email;
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
    private Socket Sclient;
    private FileManager fileManager;
    private String currentState;
    private static final String ETAT_INITIALISATION = "initialisation";
    private static final String ETAT_ATTENTE_EHLO = "attente ehlo";
    private static final String ETAT_ATTENTE_MAIL = "attente mail";
    private static final String ETAT_ATTENTE_RCPT = "attente rcpt";
    private static final String ETAT_ATTENTE_DATA = "attente data";
    private static final String ETAT_ATTENTE_CONTENT = "attente contenu";
    private Email mail;

    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        fileManager = new FileManager();
        mail = new Email();
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
        this.sendMessage(220, "SMTP service ready");
        currentState = ETAT_ATTENTE_EHLO;
        try {
            while (!quit_asked) {
                BIS.read(buffer);
                received = new String(buffer);
                switch (currentState) {
                    case ETAT_ATTENTE_EHLO:
                        quit_asked = manageAttenteEhloState(received);
                        break;
                    case ETAT_ATTENTE_MAIL:
                        quit_asked = manageAttenteMailoState(received);
                        break;
                    case ETAT_ATTENTE_RCPT:
                        quit_asked = manageAttenteRcptState(received);
                        break;
                    case ETAT_ATTENTE_DATA:
                        quit_asked = manageAttenteDataState(received);
                        break;
                    case ETAT_ATTENTE_CONTENT:
                        quit_asked = manageAttenteContentState(received);
                        break;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean manageAttenteEhloState(String received) {
        if (received.startsWith("EHLO")) {
            this.sendMessage(250, "OK");
            currentState = ETAT_ATTENTE_MAIL;
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            return true;
        } else {
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
            this.sendMessage(503, "bad sequence of command");
        }
        return false;
    }

    public boolean manageAttenteMailoState(String received) {
        if (received.startsWith("MAIL")) {
            mail.setExpediteur(received.split("<")[1].split(">")[0]);
            this.sendMessage(250, "sender ok");
            currentState = ETAT_ATTENTE_RCPT;
        } else if (received.startsWith("QUIT")) {
            //TODO FERMER LE SERVEUR : FAIRE UNE METHODE POUR TOUT CLOSE PROPREMENT
            this.sendMessage(221, "Connexion closed");
            return true;
        } else if (received.startsWith("RSET")) {
            this.sendMessage(250, "Reseted");
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
        } else {
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
            this.sendMessage(503, "bad sequence of command");

        }
        return false;
    }

    public boolean manageAttenteRcptState(String received) {
        if (received.startsWith("RCPT")) {
            String temp = received.split("<")[1].split(">")[0];
            if (fileManager.getUserNames().contains(temp)) {
                mail.addDestinataire(temp);
                this.sendMessage(250, "receiver ok");
                currentState = ETAT_ATTENTE_DATA;
            } else {
                this.sendMessage(550, "no such user");
            }
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            return true;
        } else if (received.startsWith("RSET")) {
            mail = new Email();
            currentState = ETAT_ATTENTE_MAIL;
            //NETTOYER sender, receivers, object, data
            //retourner dans attente
            clearContext();
            this.sendMessage(250, "Reseted");
        } else {
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
            this.sendMessage(503, "bad sequence of command");
        }
        return false;
    }

    public boolean manageAttenteDataState(String received) {
        if (received.startsWith("RCPT")) {
            String temp = received.split("<")[1].split(">")[0];
            if (fileManager.getUserNames().contains(temp)) {
                mail.addDestinataire(temp);
                this.sendMessage(250, "receiver ok");
            } else {
                this.sendMessage(550, "no such user");
            }
        } else if (received.startsWith("DATA")) {
            this.sendMessage(354, "Enter mail, end with \".\" on a line by itself");
            currentState = ETAT_ATTENTE_CONTENT;
        } else if (received.startsWith("QUIT")) {
            this.sendMessage(221, "Connexion closed");
            return true;
        } else if (received.startsWith("RSET")) {
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
            this.sendMessage(250, "Reseted");
        } else {
            //NETTOYER sender, receivers, object, data
            //retourner dans attente mail
            clearContext();
            this.sendMessage(503, "bad sequence of command");
        }
        return false;
    }

    public boolean manageAttenteContentState(String received) {
        if (received.startsWith("<OBJECT>") || received.startsWith("Date")) {
            parseContentMessage(received);
        } else if (received.startsWith("QUIT")) {
            sendMessage(250, "server is disconnecting");
            return true;
        } else {
            mail.setObjet("sans objet");
            String[] corps = received.split("\n");
            for (int i = 2; i < corps.length; i++) {
                if (!corps[i].equals(".")) {
                    if (mail.getMessage().length() != 0) {
                        mail.setMessage(mail.getMessage() + "\n" + corps[i]);
                    } else {
                        mail.setMessage(corps[i]);
                    }
                }
            }
        }

        //APPEL AU FILEMANAGER POUR STOCKER LE MAIL
        fileManager.writeMail(mail);
        return false;
    }

    /**
     * Méthode d'envoi d'un message au client
     *
     * @param errorCode : code d'erreur d'entête du message
     * @param message : message descriptif ajouté
     */
    public void sendMessage(int errorCode, String message) {
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            String mess = errorCode + " " + message + "\r\n";
            this.BOS.write(mess.getBytes());
            this.BOS.flush();
            System.out.println("J'envoie : " + mess);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearContext() {
        this.mail = new Email();
        currentState = ETAT_ATTENTE_MAIL;
    }

    public void parseContentMessage(String content) {
        String[] lines = content.split("\n");
        boolean obj = false;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith("Objet :")) {
                mail.setObjet(line.substring("Objet : ".length()));
                obj = true;
            } else if (line.startsWith("<OBJECT>")) {
                mail.setObjet(line.substring("Objet : ".length()));
                obj = true;
            } else
            if(obj == true && !line.equals(".")){
                mail.setMessage(mail.getMessage() != null ? mail.getMessage()+line+"\n" : line);
            }
        }
    }
}
