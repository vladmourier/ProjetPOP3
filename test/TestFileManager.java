/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import POP3_SMTP.FileManager;
import POP3_SMTP.Email;
import java.util.ArrayList;

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
        Email e = new Email();
        ArrayList<String> destinataires = new ArrayList<>();
        destinataires.add("User");
        destinataires.add("Toto");
        destinataires.add("Adrien");
        e.setExpediteur("vlad@mourier.com");
        e.setObjet("Test de la fonction d'écriture");
        e.setMessage("Je teste pour voir si ce message va s'écrire dans les différents fichiers ");
        e.setDestinataires(destinataires);
        e.setId(10);
        
        FM.writeMail(e);
    }
}
