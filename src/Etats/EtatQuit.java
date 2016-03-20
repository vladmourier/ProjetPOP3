/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etats;

import java.io.IOException;
import pop3.Client;

/**
 *
 * @author mathieu
 */
public class EtatQuit {

    public EtatQuit(Client c) throws IOException {
        System.out.println(" ---- QUIT ----");
        c.envoiMsg("QUIT");
    }
    
}
