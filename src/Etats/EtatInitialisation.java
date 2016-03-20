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
public class EtatInitialisation {
    
    Client client;
    public EtatInitialisation(Client c) throws IOException {
        client = c;
        run();
    }
    
    public void run() throws IOException{
        String s = client.receive();
        System.out.println(s);
        if (!s.contains("+OK POP3 SERVER READY")){
            System.out.println("Authorisation refusee..\nFermeture de la session");//à gérer
            return;
        }
        //faire lecture de la console et envoi des crédentials
        String user = client.lectureConsole("Veuillez entrer votre nom d'utilisateur: ");
        if (client.quit){
            EtatQuit quit = new EtatQuit(client);
        }
        String mdp = client.lectureConsole("Veuillez entrer votre mot de passe: ");
        if (client.quit){
            EtatQuit quit = new EtatQuit(client);
        }
        client.envoiMsg("APOP " + user + " " + mdp);
        EtatApopEnvoye apopEnvoi = new EtatApopEnvoye(client);
    }
    
}
