/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3.Server;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ClientPackage.Email;

/**
 *
 * @author Adrien
 */
public class FileManager {

    final String mail = "../Mail.txt";
    final String user_pass = "../User_Pass.txt";
    
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
                if (usr.equals(user[0]))
                {
                    id = Integer.parseInt(user[2]);
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id; 
    }
    
    public boolean verifyPass (int id, String pass) throws IOException
    {
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
                if (id == Integer.parseInt(user[2]) && pass.equals(user[1]))
                {
                    ok = true;
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ok;
    }
    
    public Email[] getMails (int id) throws IOException
    {
        Email[] mailList = null;
        
        try {
            InputStream ips = new FileInputStream(mail);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            String[] bloc = null;
            int i = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                //il faut récupérer bloc par bloc pour générer les mails proprement
                bloc[i] = line;
                i++;
                if(line.equals("\r\n\r\n"))
                {
                    //il faut parser dans le bloc les informations id, expéditeur, destinataire, objet, corps, \r\n\r\n
                    
                    String sender = bloc[1].split("MAIL FROM:")[0];//commencer à lire à partir du char n°10
                    String[] receiver = bloc[2].split("RCPT TO:, "); //commencer à lire à partir du char n°8
                    i = 0;
                    bloc = null;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mailList;
    }
}
