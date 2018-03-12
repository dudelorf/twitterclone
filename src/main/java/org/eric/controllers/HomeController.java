package org.eric.controllers;

import java.util.List;
import org.apache.velocity.VelocityContext;
import org.eric.models.Post;
import org.eric.models.User;
import org.eric.services.PostService;
import org.eric.services.UserService;

public class HomeController extends BaseController {

    protected final UserService userService;

    protected final PostService postService;
    
    public HomeController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    public String getHomePage(User currentUser) {
        VelocityContext context = getHomePageData(currentUser);
        String page = renderView(context, "pages/home.vm");
        return page;
    }

    /**
     * Gathers all data for page and adds it to context for view
     * 
     * @param currentUser current application user
     */
    private VelocityContext getHomePageData(User currentUser) {
        VelocityContext context = new VelocityContext();

        List<Post> newPosts = postService.getNewPosts(currentUser.getId());
        context.put("posts", newPosts);

        context.put("user", currentUser);
        
        return context;
    }

}