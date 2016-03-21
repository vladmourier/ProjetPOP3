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
        System.out.println("---- Accueil ----");
        String queFaire = null;
        while (!quit) {//null == queFaire && 
            try {
                System.out.println("Que voulez vous faire?");
                System.out.println(" 1 - Lire un message");
                System.out.println(" 2 - Supprimer un message");
                System.out.println(" 0 - Quitter");
                int res = scanner.nextInt();
                if (res == 1) {
                    System.out.println("Quel message voulez vous lire?");
                    int lire = scanner.nextInt();
                    client.envoiMsg("RETR " + lire);
                } else if (res == 2) {
                    System.out.println("Quel message voulez vous supprimer?");
                    int suppr = scanner.nextInt();
                    client.envoiMsg("DEL " + suppr);
                } else if (res == 0) {
                    quit = true;
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error: " + e.getMessage());
            }
            String reponse = client.receive();
            System.out.println(reponse);
        }
        if (quit){
            EtatQuit quitter = new EtatQuit(client);
        }
    }
}
