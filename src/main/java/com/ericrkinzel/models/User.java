package com.ericrkinzel.models;

import java.sql.Timestamp;

public class User {

    private int id = -1;
    private String email = "";
    private String username = "";
    private String firstname = "";
    private String lastname = "";
    private String salt = "";
    private String password = "";
    private String token = "";
    private Timestamp tokenExpiration = null;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Timestamp tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getFirstname() {
            return firstname;
    }

    public void setFirstname(String firstname) {
            this.firstname = firstname;
    }

    public String getLastname() {
            return lastname;
    }

    public void setLastname(String lastname) {
            this.lastname = lastname;
    }
}
