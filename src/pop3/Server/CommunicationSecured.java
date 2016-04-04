/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pop3.Server;

import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Vladimir
 */
public class CommunicationSecured extends Communication {

    public CommunicationSecured(Socket client) throws SocketException {
        super(client);
    }
    
}
