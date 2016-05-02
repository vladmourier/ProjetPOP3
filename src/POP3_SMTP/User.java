/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package POP3_SMTP;

/**
 *
 * @author Vladimir
 */
public class User {
    private int id;
    private String username;
    private String pass;

    public User(){
        
    }
    
    public User(int id, String username, String pass) {
        this.id = id;
        this.username = username;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", pass=" + pass + '}';
    }
    
    
    
}
