/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3;

import java.util.ArrayList;

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
    private String message;

    public Email(String expediteur, ArrayList<String> destinataires, String message) {
        this.expediteur = expediteur;
        this.destinataires = destinataires;
        this.message = message;
    }

    public Email(String expediteur, String destinataires, String message) {
        this.expediteur = expediteur;
        ArrayList<String> s = new ArrayList<>();
        s.add(destinataires);
        this.destinataires = s;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
