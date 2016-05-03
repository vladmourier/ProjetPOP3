/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.SMTP.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrien
 */
public class SMTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Serveur s;
        try {
            s = new Serveur(25000);
                   s.run(false);

        } catch (IOException ex) {
            Logger.getLogger(SMTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
