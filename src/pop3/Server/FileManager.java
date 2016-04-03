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
                //System.out.println(line);
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
                //System.out.println(line);
                //il faut parser line entre chaque tabulation pour tester
                if(!"".equals(line)){
                    String[] user = line.split("\t");
                    if (id == Integer.parseInt(user[2])) {
                        if (pass.equals(user[1])) {
                            ok = true;
                        }
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ok;
    }
    
    public long getMailsSize(int idu){
        File f = new File(idu+".txt");
        //TODO
        //Enlever les numéros de messages et les retour à la ligne pour resultat exact
        return f.length();
    }
    
    public ArrayList<Email> getMails(int idu){
        ArrayList<Email> mailList = new ArrayList();
        try {
            InputStream ips = new FileInputStream(idu + ".txt");
            System.out.println("Ouverture de : " + idu + ".txt");
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            Email mail = new Email();
            int lineId=0;
            String line, body ="";
            while((line= br.readLine()) != null){
                if(line.equals("\\r\\n\\r\\n")){
                    mail.setMessage(body);
                    mailList.add(mail);
                    mail = new Email();
                    lineId = 0;
                    body = "";
                } else
                if(line.startsWith("MAIL FROM:")){
                    String exp = line.split(":")[1];
                    exp= exp.substring(1, exp.length()-1);
                    mail.setExpediteur(exp);
                } else
                if(line.startsWith("RCPT TO:")){
                    for(String s : line.split(":")[1].split(",")){
                        mail.addDestinataire(s);
                    }
                } else
                if(line.startsWith("<OBJECT>")){
                    mail.setObjet(line.substring("<OBJECT>".length()));
                } else if (lineId==0) {
                    mail.setId(Integer.parseInt(line));
                } else {
                    body += line;
                }
                lineId++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return mailList;
    }
    
    public boolean deleteMail(int idu, int idm) {
        boolean dele = false;
        
        try {
            //Fichier temporaire, on recopir toutes les lignes sauf le mail à delete
            File fTemp = new File(idu + "'.txt");
            System.out.println("Création du fichier temporaire de mail " + idu + "'.txt : " + fTemp.createNewFile());
            FileOutputStream ops = new FileOutputStream(fTemp);
            OutputStreamWriter opsw = new OutputStreamWriter(ops);
            BufferedWriter bw = new BufferedWriter(opsw);
            
            //Fichier de mail complet de l'utilisateur
            System.out.println("Ouverture de : " + idu + ".txt");
            File f = new File(idu + ".txt");
            FileInputStream ips = new FileInputStream(f);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            
            //Lecture du fichier et réécriture
            String line;
            boolean newMail = true;
            boolean goodMail = true;
            
            while ((line = br.readLine()) != null) {
                
                //si on entame un nouveau mail on vérifie que ce ne soit pas celui à supprimer
                if (newMail == true) {
                    //System.out.println(newMail);
                    newMail = false;
                    if (Integer.parseInt(line) == idm) {
                        goodMail = false;
                        dele = true;
                        //System.out.println("detection mauvais mail à ne pas copier");
                    } else {
                        goodMail = true;
                    }
                }
                
                //si on est dans un mail à garder, on le recopie simplement
                if (goodMail == true) {
                    //System.out.println("goodMail est true");
                    bw.write(line);
                    bw.write("\r\n");
                    bw.flush();
                }
                
                //Si on atteint une fin de mail, on le signale pour la prochaine lecture
                if (line.equals(".")) {
                    newMail = true;
                }
            }
            
            //On ferme les buffers et flux
            br.close();
            ipsr.close();
            ips.close();
            bw.close();
            opsw.close();
            ops.close();
            
            //on peut supprimer le fichier inital et renommer le fichier temporaire
            f.delete();
            System.out.println("Renommage : "+fTemp.renameTo(f));
            f = new File(idu + "'.txt");
            f.delete();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dele;
    }
    /*
    public boolean writeMail(int idu, Email mail)
    {
    boolean wri = false;
    
    try{
    
    }
    
    return wri;
    }
    */
}
