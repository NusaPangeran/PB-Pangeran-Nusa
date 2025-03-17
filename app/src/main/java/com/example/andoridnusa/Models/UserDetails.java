package com.example.andoridnusa.Models;

public class UserDetails {
    private String userId, username, userPassword, userNIM, userEmail;

    public UserDetails(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNIM() {
        return userNIM;
    }

    public void setUserNIM(String userNIM) {
        this.userNIM = userNIM;
    }

    public UserDetails(String userId, String username, String userPassword, String userNIM, String userEmail){
        this.userId = userId;
        this.username = username;
        this.userPassword = userPassword;
        this.userNIM = userNIM;
        this.userEmail = userEmail;
    }
}
