/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3;

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
        this.expediteur = mailSplited[0].split("<")[1].substring(0, mailSplited[0].split("<")[1].length()-1);
        String[] dests = mailSplited[1].split(":")[1].split(",");
        this.destinataires = new ArrayList<>();
        this.destinataires.addAll(Arrays.asList(dests));
        this.objet = mailSplited[2].split(">")[1];
        this.message = "";
        for (int i = 3; i<= mailSplited.length -2; i++){
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
    
    
}
