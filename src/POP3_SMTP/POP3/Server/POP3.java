/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.POP3.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladimir
 */
public class POP3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Serveur s;
        try {
            s = new Serveur(110);
                   s.run(false);

        } catch (IOException ex) {
            Logger.getLogger(POP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
