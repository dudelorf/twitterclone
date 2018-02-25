package org.eric.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.models.Post;

public class PostService extends BaseService{

    public PostService(BasicDataSource datasource) {
        super(datasource);
    }
    
    public Post getPost(int postId){
        return new Post();
    }
    
    public boolean updatePost(Post post){
        return true;
    }
    
    public Post saveNewPost(Post post){
        return post;
    }
    
    public List<Post> getUserPosts(int userId){
        return new ArrayList<>();
    }
    
    public List<Post> getNewPosts(int userId){
        return new ArrayList<>();
    }
    
}
