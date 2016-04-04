/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pop3.Email;

/**
 *
 * @author Adrien
 */
public class TestFileManager {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileManager FM = new FileManager();
        try {
            ArrayList<Email> MailList = new ArrayList();
            
            System.out.println("Le user Adrien a pour id : "+FM.findUserId("Adrien"));
            System.out.println("Le pass est : "+FM.verifyPass(3, "motdepasse"));
            System.out.println("Le pass est : "+FM.verifyPass(3, "test"));
            MailList = FM.getMails(1);
            System.out.println("Taille des mails = " + FM.getMailsSize(1));
            for(Email e: MailList){
                System.out.println("Taille d'un mail = " + e.getSize());
            }
            
            
            //System.out.println(FM.deleteMail(1, 1));
            
        } catch (IOException ex) {
            Logger.getLogger(TestFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
