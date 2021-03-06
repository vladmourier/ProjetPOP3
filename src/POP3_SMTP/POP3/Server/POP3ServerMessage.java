/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POP3_SMTP.POP3.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import POP3_SMTP.Email;

/**
 *
 * @author Vladimir
 */
public class POP3ServerMessage {

    /**
     * Le corps du message server ; Doit commencer par +OK ou -ERR
     */
    public String message;

    ///
    /// Constantes identifiant les messages que le serveur doit envoyer
    ///
    public static final String SERVER_READY = "+OK POP3 SERVER READY";
    public static final String SERVER_SIGNING_OFF = "+OK SERVER SIGNING OFF";
    public static final String SERVER_WAITING_FOR_PASS = "+OK POP3 SERVER WAITING FOR PASS";
    public static final String SERVER_INIT_MAILBOX = "+OK MAILBOX INITIALIZED";
    public static final String SERVER_SEND_INFOS = "sending infos";
    public static final String SERVER_DELETING_MESSAGE = "deleting message";

    public POP3ServerMessage() {
        this.message = "";
    }

    public POP3ServerMessage(String message) {
        this.message = message;
    }

    public POP3ServerMessage getMsgServerReadyTimestamp() {
        java.util.Date date = new java.util.Date();
        this.message = SERVER_READY + " <" + date.getTime() + "@myPOP3server.com>";
        return this;
    }

    public POP3ServerMessage getMsgServerInitMailbox(ArrayList<Email> mails, int size) {
        this.message = "+OK CURRENT MAILBOX HAS " + mails.size() + " MESSAGES (" + size + " bytes)";
//        this.message += "\r\n";
//        for(Email m : mails){
//            message += mails.indexOf(m)+1 + " " + m.getSize() + "\r\n";
//        }
//        message += ".";
        return this;
    }

    public POP3ServerMessage getMsgShowingEmail(Email e) {
        this.message = "+OK " + e.getSize() + " bytes \r\n";
        message += e.getFullText() + ".";
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
