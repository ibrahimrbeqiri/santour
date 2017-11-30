package models;

import java.io.Serializable;

/**
 * Created by Vincent_2 on 25.11.2017.
 */

public class User implements Serializable{

    private String idUser;
    private String userName;
    private String userPwd;
    private String role;


    public User(){

    }

    public User(String userName, String userPwd, String role) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
