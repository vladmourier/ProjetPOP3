/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3.Server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("Le user Adrien a pour id : "+FM.findUser("Adrien"));
            System.out.println("Le pass est : "+FM.verifyPass(3, "motdepasse"));
            System.out.println("Le pass est : "+FM.verifyPass(3, "test"));
            FM.getMails(1);
            System.out.println(FM.deleteMail(1, 1));
            
        } catch (IOException ex) {
            Logger.getLogger(TestFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
