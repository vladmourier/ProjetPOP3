/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3.Server;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pop3.*;

/**
 *
 * @author Adrien
 */
public class FileManager {

    final String user_pass = "User_Pass.txt";

    public int findUser(String usr) throws IOException {
        int id = 0;

        try {
            InputStream ips = new FileInputStream(user_pass);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                //il faut parser line entre chaque tabulation pour tester
                String[] user = line.split("\t");
                if (usr.equals(user[0])) {
                    id = Integer.parseInt(user[2]);
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public boolean verifyPass(int id, String pass) throws IOException {
        boolean ok = false;

        try {
            InputStream ips = new FileInputStream(user_pass);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                //il faut parser line entre chaque tabulation pour tester
                String[] user = line.split("\t");
                if (id == Integer.parseInt(user[2])) {
                    if (pass.equals(user[1])) {
                        ok = true;
                    }
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ok;
    }

    public ArrayList<Email> getMails(int idu) throws IOException {
        ArrayList<Email> mailList = null;

        try {
            InputStream ips = new FileInputStream(idu + ".txt");
            System.out.println("Ouverture de : " + idu + ".txt");
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;

            ArrayList<String> bloc = new ArrayList();
            int i = 0;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                //il faut récupérer bloc par bloc pour générer les mails proprement
                bloc.add(line);
                i++;

                if (line.equals("\r\n\r\n")) {
                    int idm = Integer.parseInt(bloc.get(0));
                    //commencer à lire à partir du char n°10, jusqu'à la fin
                    String sender = bloc.get(1).substring(10, bloc.get(1).length());
                    //commencer à lire à partir du char n°8 jusqu'à la fin, et parser selon les virgules
                    String[] receiver = (bloc.get(2).substring(8, bloc.get(2).length())).split(",");
                    ArrayList<String> rcpt = null;
                    for (String ln : receiver) {
                        rcpt.add(ln);
                    }
                    //commencer à lire à partir du char n°8 jusqu'à la fin
                    String object = bloc.get(3).substring(8, bloc.get(3).length());
                    //Récupération du corps du mail
                    String mail = null;
                    for (int c = 4; c > bloc.size(); c++) {
                        mail.concat(bloc.get(c));
                    }
                    Email mailTemp = new Email(idu, sender, rcpt, object, mail);
                    mailList.add(mailTemp); //fin traitement du mail, on passe au suivant
                    i = 0;
                    bloc = null;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mailList;
    }

    public boolean deleteMail(int idu, int idm) {
        boolean suppr = false;

        try {
            //Fichier temporaire, on recopir toutes les lignes sauf le mail à delete
            File fTemp = new File(idu + "'.txt");
            System.out.println("Création du fichier temporaire de mail " + idu + "'.txt : " + fTemp.createNewFile());
            FileOutputStream ops = new FileOutputStream(idu + "'.txt");
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);

            //Fichier de mail complet de l'utilisateur
            FileInputStream ips = new FileInputStream(idu + ".txt");
            System.out.println("Ouverture de : " + idu + ".txt");
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            //Lecture du fichier et réécriture
            String line;
            boolean newMail = true;
            boolean goodMail = true;
            while ((line = br.readLine()) != null) {
                //si on entame un nouveau mail on vérifie que ce ne soit pas celui à supprimer
                if (newMail == true) {
                    newMail = false;
                    if (Integer.parseInt(line) == idm) {
                        goodMail = false;
                        suppr = true;
                    }
                    else
                    {
                        goodMail = true;
                    }
                }
                
                //si on est dans un mail à garder, on le recopie simplement
                if (goodMail == true) {
                    System.out.println("goodMail passe à true");
                    bw.write(line);
                    bw.flush();
                }
                
                //Si on atteint une fin de mail, on le signale pour la prochaine lecture
                if (line.equals("\r\n\r\n")) {
                    newMail = true;
                }
            }
            
            //on peut supprimer le fichier inital et renommer le fichier temporaire
            File f = new File(idu+"'/txt");
            fTemp.renameTo(f);
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return suppr;
    }

}
