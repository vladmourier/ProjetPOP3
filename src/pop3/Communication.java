/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vladimir
 */
public class Communication extends ObjetConnecte implements Runnable {

    public int port_dest;
    public int port_ecoute;
    public InetAddress address_dest;
    private Socket Sclient;

    public Communication(Socket client) throws SocketException {
        super();
        port_dest = client.getPort();
        this.Sclient = client;
        this.address_dest = client.getInetAddress();
        System.out.println("Communication creee avec le Client : " + address_dest + " | " + port_dest);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        String racine = "web";
        try {
            this.OS = Sclient.getOutputStream();
            this.BOS = new BufferedOutputStream(this.OS);
            this.IS = Sclient.getInputStream();
            this.BIS = new BufferedInputStream(this.IS);
            System.out.println("J'attend un requ�te");
            this.BIS.read(buffer);
            System.out.println(new String(buffer));
            //Pour recuperer le fichier cible : on split new String(buffer) selon GET
            //puis on split la deuxième string avec http et on prend la première
            String chemin = new String(buffer).split("GET")[1].split("HTTP")[0];
            String adresseFichier = racine+chemin.substring(1);

            RandomAccessFile monFichier = new RandomAccessFile(adresseFichier, "r");

            this.BOS.write("HTTP/1.1 200 OK\r\n".getBytes());
            this.BOS.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n".getBytes());
            this.BOS.write("Server: Apache/0.8.4\r\n".getBytes());
            if (adresseFichier.endsWith("jpg")) {
                this.BOS.write("Content-Type: image/jpeg\r\n".getBytes());
            } else if (adresseFichier.endsWith("html")) {
                this.BOS.write("Content-Type: text/html\r\n".getBytes());
            }
            this.BOS.write(new String("Content-Length: " + monFichier.length() + "\r\n").getBytes());
            this.BOS.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n".getBytes());
            this.BOS.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n".getBytes());
            this.BOS.write("\r\n".getBytes());
            Byte a = null;
            try {
                a = monFichier.readByte();
            } catch (EOFException e) {
                System.out.println(e.getMessage());
            }
            boolean b = true;

            while (b) {
                this.BOS.write(a);
                try {
                    a = monFichier.readByte();
                } catch (EOFException e) {
                    b = false;
                }
            }
            System.out.println("Je r�pond � la requ�te");
            this.BOS.flush();

        } catch (IOException ex) {
            System.out.println("Fichier introuvable");
            try {
                RandomAccessFile monFichier = new RandomAccessFile("web/404.html", "r");
                this.BOS.write("HTTP/1.1 404 Not Found\r\n".getBytes());
                this.BOS.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n".getBytes());
                this.BOS.write("Server: Apache/0.8.4\r\n".getBytes());
                this.BOS.write("Content-Type: text/html\r\n".getBytes());
                this.BOS.write(new String("Content-Length: " + monFichier.length() + "\r\n").getBytes());
                this.BOS.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n".getBytes());
                this.BOS.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n".getBytes());
                this.BOS.write("\r\n".getBytes());
                
                 Byte a = null;
            try {
                a = monFichier.readByte();
            } catch (EOFException e) {
                System.out.println(e.getMessage());
            }
            boolean b = true;

            while (b) {
                this.BOS.write(a);
                try {
                    a = monFichier.readByte();
                } catch (EOFException e) {
                    b = false;
                }
            }
                this.BOS.write("\r\n".getBytes());
                this.BOS.flush();
                
            } catch (IOException ex1) {
                Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void response() throws SocketException {
    }

    public int getPort_dest() {
        return port_dest;
    }

    public void setPort_dest(int port_dest) {
        this.port_dest = port_dest;
    }

    public InetAddress getAddress_dest() {
        return address_dest;
    }

    public void setAddress_dest(InetAddress address_dest) {
        this.address_dest = address_dest;
    }
}
