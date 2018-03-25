package org.eric.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.dbcp.BasicDataSource;
import org.eric.models.Post;
import org.eric.models.Subscription;

public class PostService extends BaseService{

    private SubscriptionService subscriptionService;

    public PostService(BasicDataSource datasource, SubscriptionService subscriptionService) {
        super(datasource);
        this.subscriptionService = subscriptionService;
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
    
    public List<Post> getUserPosts(int userId, int offset){
        return new ArrayList<>();
    }
    
    public List<Post> getNewPosts(int userId){
        List<Subscription> subscriptions = subscriptionService.getSubscriptions(userId);

        List<Post> posts = subscriptions.stream()
                            .map((s) -> {
                                Post p = new Post();
                                p.setPostBody(String.valueOf(s.getPosterId()));
                                return p;
                            }).collect(Collectors.toList());

        return posts;
    }
    
}
