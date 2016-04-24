/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Server;

import POP3_SMTP.ObjetConnecte;

/**
 *
 * @author Vlad
 */
public class Communication extends ObjetConnecte implements Runnable {

    private static final String ETAT_INITIALISATION = "initialisation"; 
    private static final String ETAT_ATTENTE_EHLO = "attente ehlo"; 
    private static final String ETAT_ATTENTE_MAIL = "attente mail"; 
    private static final String ETAT_ATTENTE_RCPT = "attente rcpt"; 
    private static final String ETAT_ATTENTE_DATA = "attente data"; 
    private static final String ETAT_ATTENTE_CONTENT = "attente contenu"; 
    
    @Override
    public void run() {
        
    }
    
}
