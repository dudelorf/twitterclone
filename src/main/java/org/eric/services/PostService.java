package org.eric.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.eric.models.Post;
import org.eric.models.Subscription;

public class PostService extends BaseService{

    private SubscriptionService subscriptionService;

    public PostService(BasicDataSource datasource, SubscriptionService subscriptionService) {
        super(datasource);
        this.subscriptionService = subscriptionService;
    }

    /**
     * Saves the post
     * 
     * post date is set to the time post is saved
     * 
     * @return success of save operation
     */
    public boolean saveNewPost(Post post){
        String sql = "INSERT INTO POSTS "
                   + "( "
                   + " userId, "
                   + " postBody, "
                   + " postDate "
                   + " ) VALUES "
                   + " (?, ?, NOW()) ";
        
        return update(sql,
                      post.getUserId(),
                      post.getPostBody()) != -1;
    }
    
    /**
     * Updates the post
     * 
     * @return success of save operation
     */
    public boolean updatePost(Post post){
        String sql = "UPDATE posts SET "
                   + " postBody = ? "
                   + " WHERE id = ? ";
        return update(sql, 
                      post.getPostBody(),
                      post.getId()) != -1;
    }

    /**
     * Deletes the post
     * 
     * @param postId post to delete
     * @return success of delete
     */
    public boolean deletePost(int postId){
        String sql = "DELETE FROM posts "
                   + "WHERE id = ? ";

        return update(sql, postId) != -1;
    }
    
    /**
     * Gets the specified post
     * 
     * If a db error or no post is found an empty post is returned
     * 
     * @param postId post to get
     * @return post details
     */
    public Post getPost(int postId){
        ResultSetHandler<Post> handler = new BeanHandler<>(Post.class);
        String sql = "SELECT * "
                   + "FROM posts "
                   + "WHERE id = ?";

        Post post = query(handler, sql, postId);
        if(post == null){
            return new Post();
        }else{
            return post;
        }
    }
    
    /**
     * Gets posts for the user
     * 
     * Returns 10 results offset by the supplied value
     * 
     * @param userId user to get posts for
     * @param offset offset of results
     */
    public List<Post> getUserPosts(int userId, int offset){
        ResultSetHandler<List<Post>> handler = new BeanListHandler<>(Post.class);

        String sql = "SELECT * "
                   + "FROM posts "
                   + "WHERE userId = ? "
                   + "LIMIT 10 "
                   + "OFFSET ? ";
        List<Post> posts = query(handler, sql, userId, offset);
        if(posts == null){
            return new ArrayList<>();
        }else{
            return posts;
        }
    }

    /**
     * Gets 10 most recent posts in the system
     * 
     * Used to populate post list when user has no subscriptions
     * 
     * @return list of 10 most recent posts
     */
    public List<Post> getRecentSystemPosts(){
        ResultSetHandler<List<Post>> handler = new BeanListHandler<>(Post.class);

        String sql = " SELECT * "
                   + " FROM posts "
                   + " ORDER BY postDate "
                   + " LIMIT 10 ";

        List<Post> posts = query(handler, sql);
        if(posts == null){
            return new ArrayList<Post>();
        }else{
            return posts;
        }
    }
    
    /**
     * Gets posts for the homepage
     * 
     * Attempts to get posts based on subscriptions. In the event no subscriptions
     * have been set yet this will get 10 most recent posts in the system.
     * 
     * @param userId logged in user
     * @return list of posts for homepage
     */
    public List<Post> getNewPosts(int userId){
        List<Subscription> subscriptions = subscriptionService.getSubscriptions(userId);
        List<Post> posts = null;

        if(subscriptions.size() > 0){
            //get latest posts from subscribed users
            posts = getRecentSystemPosts();
        }else{
            //get latest posts
            posts = getRecentSystemPosts();
        }

        return posts;
    }
    
}
