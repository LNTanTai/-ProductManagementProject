/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author cutay
 */
public class LoginDTO {
    String userID, fullname, password;
    boolean status;

    public LoginDTO() {
    }

    public LoginDTO(String userID, String fullname, String password, boolean status) {
        this.userID = userID;
        this.fullname = fullname;
        this.password = password;
        this.status = status;
    }

    public LoginDTO(String userID, String fullname, String password) {
        this.userID = userID;
        this.fullname = fullname;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
