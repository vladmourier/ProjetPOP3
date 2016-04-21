/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package POP3_SMTP;

import java.security.MessageDigest;

/**
 *
 * @author Vlad
 */
public class Tools {
    public static String encrypt(String message)
    {
        String digest = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2*hash.length);
            
            for(byte b : hash)
            {
                sb.append(String.format("%02x", b&0xff));
            }
            
            digest = sb.toString();
        }
        catch (Exception e)
        {
            //Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        
        return digest;
        
    }
}
