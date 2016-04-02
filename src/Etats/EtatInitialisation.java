/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etats;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import pop3.Client;
import pop3.ConnexionFrame;

/**
 *
 * @author mathieu
 */
public class EtatInitialisation {

    Client client;
    ConnexionFrame fenetre;

    public EtatInitialisation(Client c) throws IOException {
        client = c;
    }

    public void connection(String user, String mdp) throws IOException {
        client.changementEtat(client.ETAT_INITIALISATION);
        String s = client.receive("\r\n");
        System.out.println(s);
        if (!s.contains("+OK")) {
            System.out.println("Connexion au serveur échouée..\nFermeture de la session.");//à gérer
            return;
        }
        //faire lecture de la console et envoi des crédentials
        int test = 0; //jusqu'a 5 essais
        boolean passed = false;
        while (test < 5 && !passed) {
            client.envoiMsg("APOP " + user + " " + mdp);
            s = client.receive("\r\n");
            System.out.println(s);
            if (s.contains("+OK")){
                passed = true;
            }
            else if (test<4){
                int x = 4 - test;
                System.out.println("Mot de passe erroné\nVous avez encore " + x + " essais");
            }
            else{
                System.out.println("Mot de passe erroné\nTrop de tentatives, Fermeture de la session.");
                EtatQuit quit = new EtatQuit(client);
            }
            test++;
        }
        int nbMsg = parseInt(s.split(" ")[4]);
        EtatTransaction transac = new EtatTransaction(client, nbMsg);
    }

}
