package org.eric.models;

import java.sql.Timestamp;

public class Post {
    
    private int user_id;
    private String post_body;
    private Timestamp post_date;

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getPostBody() {
        return post_body;
    }

    public void setPostBody(String post_body) {
        this.post_body = post_body;
    }

    public Timestamp getPostDate() {
        return post_date;
    }

    public void setPostDate(Timestamp post_date) {
        this.post_date = post_date;
    }
    
    
}
