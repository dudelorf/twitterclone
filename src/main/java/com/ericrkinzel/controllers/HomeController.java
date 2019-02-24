package com.ericrkinzel.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.models.Post;
import com.ericrkinzel.models.User;
import com.ericrkinzel.services.PostService;
import com.ericrkinzel.services.UserService;

/**
 * Handles home routes
 */
public class HomeController extends BaseController {

    protected final UserService userService;

    protected final PostService postService;
    
    public HomeController(
		UserService userService,
		PostService postService,
		ServletContext context
	) {
    	super(context);
        this.userService = userService;
        this.postService = postService;
    }

    /**
     * Renders the home page
     * 
     * @param currentUser current active user
     * @return rendered view
     */
    public String getHomePage(User currentUser) {
        VelocityContext context = getHomePageData(currentUser);
        String page = renderView(context, "pages/home.vm");
        return page;
    }

    /**
     * Gathers all data for page and adds it to context for view
     * 
     * @param currentUser current application user
     * @return populated context for template
     */
    private VelocityContext getHomePageData(User currentUser) {
        VelocityContext context = new VelocityContext();

        List<Post> newPosts = postService.getNewPosts();
        context.put("posts", newPosts);

        context.put("user", currentUser);
        
        return context;
    }

}