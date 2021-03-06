package com.ericrkinzel.models;

import java.sql.Timestamp;

/**
 * Single post model
 */
public class Post {
    
    private int id;
    private int userId;
    private String userName;
    private String postBody;
    private Timestamp postDate;

    public int getId(){
        return id;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }
    
    
}
