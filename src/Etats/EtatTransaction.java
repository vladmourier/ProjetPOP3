/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etats;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pop3.Client;

/**
 *
 * @author mathieu
 */
public class EtatTransaction {

    Client client;
    int nbMsg;
    boolean quit;
    Scanner scanner;

    public EtatTransaction(Client c, int nbMsg) {
        client = c;
        this.nbMsg = nbMsg;
        scanner = new Scanner(System.in);
        try {
            run();
        } catch (IOException ex) {
            Logger.getLogger(EtatTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() throws IOException {
        client.changementEtat(client.ETAT_TRANSACTION);
        System.out.println("Vous avez " + nbMsg + " messages dans votre boite mail.\n");
        String queFaire = null;
        while (!quit) {//null == queFaire && 
            try {        
                System.out.println("------- Accueil -------");
                System.out.println("Que voulez vous faire?");
                System.out.println(" 1 - Lire un message");
                System.out.println(" 2 - Supprimer un message");
                System.out.println(" 0 - Quitter");
                System.out.println("***********************");
                int res = scanner.nextInt();
                if (res == 1) {
                    System.out.println("Quel message voulez vous lire? (0 pour retourner à l'acceuil)");
                    int lire = scanner.nextInt();
                    if (lire != 0){
                        client.envoiMsg("RETR " + lire);
                        String reponse = client.receiveMail();
                        System.out.println(reponse);
                    }
                } else if (res == 2) {
                    System.out.println("Quel message voulez vous supprimer? (0 pour retourner à l'acceuil)");
                    int suppr = scanner.nextInt();
                    if (suppr != 0){
                        client.envoiMsg("DEL " + suppr);
                        String reponse = client.receive("\r\n");
                        System.out.println(reponse);
                    }
                } else if (res == 0) {
                    quit = true;
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        if (quit){
            EtatQuit quitter = new EtatQuit(client);
        }
    }
}
