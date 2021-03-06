/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Vladimir
 */
public class Email {

    /**
     * Id du message parmi les messages d'un user donné
     */
    private int id;
    private String expediteur;
    private ArrayList<String> destinataires;
    private String objet;
    private String message;

    public Email() {
        id = -1;
        destinataires = new ArrayList<>();
        expediteur = "";
        objet = "";
        message = "";
    }

    public Email(int id, String expediteur, ArrayList<String> destinataires, String objet, String message) {
        this.id = id;
        this.expediteur = expediteur;
        this.destinataires = destinataires;
        this.objet = objet;
        this.message = message;
    }

    public Email(int id, String expediteur, String destinataires, String objet, String message) {
        this.id = id;
        this.expediteur = expediteur;
        ArrayList<String> s = new ArrayList<>();
        s.add(destinataires);
        this.destinataires = s;
        this.objet = objet;
        this.message = message;
    }

    public Email(String mail, int id) {
        this.id = id;
        String[] mailSplited = mail.split("\r\n");
        this.expediteur = mailSplited[1].split(":")[1];
        String[] dests = mailSplited[2].split(":")[1].split(",");
        this.destinataires = new ArrayList<>();
        this.destinataires.addAll(Arrays.asList(dests));
        this.objet = mailSplited[3].split(">")[1];
        this.message = "";
        for (int i = 4; i <= mailSplited.length - 2; i++) {
            this.message += mailSplited[i];
        }
    }

    public String getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(String expediteur) {
        this.expediteur = expediteur;
    }

    public ArrayList<String> getDestinataires() {
        return destinataires;
    }

    public String getDestinatairesString() {
        String dest = "";
        for (int i = 0; i < destinataires.size(); i++) {
            dest += destinataires.get(i) + "; ";
        }
        return dest;
    }

    public void addDestinataire(String destinataire) {
        destinataires.add(destinataire);
    }

    public void setDestinataires(ArrayList<String> destinataires) {
        this.destinataires = destinataires;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        //On retourne la taille en additionnant les tailles de chacuns des éléments
        int size = "MAIL FROM:<>".getBytes().length
                + "RCPT TO:".getBytes().length
                + "<OBJECT>".getBytes().length
                + this.expediteur.getBytes().length
                + this.message.getBytes().length
                + this.objet.getBytes().length;
        for (String s : destinataires) {
            size += s.getBytes().length;
            if (destinataires.lastIndexOf(s) != destinataires.size() - 1) {
                size += ",".getBytes().length;
            }
        }
        return size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullText() {
        String dest = "";
        for (String s : destinataires) {
            dest += s;
            if (destinataires.indexOf(s) != destinataires.size() - 1) {
                dest += ",";
            }
        }
        String s = "MAIL FROM: <" + expediteur + "> \r\n"
                + "RCPT TO:" + dest + "\r\n"
                + "<OBJECT>" + objet + "\r\n"
                + this.message
                + "\r\n.\r\n";
        return s;
    }

    public String getTextForWriting(int idMail) {
        String dest = "" ;
        for (String s : destinataires) {
            dest += s;
            if (destinataires.indexOf(s) != destinataires.size() - 1) {
                dest += ",";
            }
        }
        String s = idMail + "\r\n"
                + "MAIL FROM: <" + expediteur + "> \r\n"
                + "RCPT TO:" + dest + "\r\n"
                + "<OBJECT>" + objet + "\r\n\r\n"
                + this.message
                + ".\r\n";
        return s;
    }
}
