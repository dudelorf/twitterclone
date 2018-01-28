package org.eric.models;

import java.sql.Timestamp;

public class User {

    private int id = -1;
    private String username = "";
    private String salt = "";
    private String password = "";
    private String token = "";
    private Timestamp token_expiration = null;
    
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

    public Timestamp getToken_expiration() {
        return token_expiration;
    }

    public void setToken_expiration(Timestamp token_expiration) {
        this.token_expiration = token_expiration;
    }
}
