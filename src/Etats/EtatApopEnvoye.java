/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Etats;

import static java.lang.Integer.parseInt;
import pop3.Client;

/**
 *
 * @author mathieu
 */
public class EtatApopEnvoye {

    Client client;
    int nbMsg;
    
    public EtatApopEnvoye(Client c) {
        client = c;
        nbMsg = 0;
        run();
    }
    
    public void run(){
        client.changementEtat(client.ETAT_APOPENVOYE);
        // Etat APOP envoyé ------------------------------------------------------
        String s = client.receive();
        System.out.println(s);
//        if (!s.contains("+OK")){
//            System.out.println("Mauvais APOP..\nFermeture de la session");//à gérer
//        }
        //gérer le dernier "s" pour récupérer le nombre de msgs
        nbMsg = parseInt(s.split(" ")[3]);
        EtatTransaction transac = new EtatTransaction(client, nbMsg);
    }
    
}
