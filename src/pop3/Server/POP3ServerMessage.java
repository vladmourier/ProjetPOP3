/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package pop3.Server;

import pop3.Email;

/**
 *
 * @author Vladimir
 */
public class POP3ServerMessage {
    /**
     * The email address of the logged user
     */
    public String address;
    /**
     * L'email concerné par le message du serveur
     * ex : le mail concerné par un retrieve
     */
    public Email email;
    
    /**
     * Le corps du message server ; Doit commencer par +OK ou -ERR
     */
    public String message;
    /**
     * Booléen indiquant si l'action du client a pu etre effectuée
     */
    public boolean isOk;
    
    
    ///
    /// Constantes identifiant les messages que le serveur doit envoyer
    ///
    public static final String SERVER_READY = "ready";
    public static final String SERVER_SIGNING_OFF = "signing off";
    public static final String SERVER_WAITING_FOR_PASS = "waiting for pass";
    public static final String SERVER_INIT_MAILBOX = "initializing mailbox";
    public static final String SERVER_SEND_INFOS = "sending infos";
    public static final String SERVER_DELETING_MESSAGE = "deleting message";
    
    
    public POP3ServerMessage(String message, boolean isOk) {
        this.message = message;
        this.isOk = isOk;
    }
    
    public POP3ServerMessage(String message) {
        this.message = message;
    }
    
    public POP3ServerMessage getMsgServerReady(){
        this.message = "+OK POP3 server ready";
        this.isOk = true;
        return this;
    }
    
    public POP3ServerMessage getMsgServerSigningOff(){
        this.message = "+OK POP3 server signing off";
        this.isOk = true;
        return this;
    }
    
    public POP3ServerMessage getMsgServerInitMailbox(){
        return null;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isIsOk() {
        return isOk;
    }
    
    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public void setEmail(Email email) {
        this.email = email;
    }
    
}
