package org.eric.controllers;

import org.apache.velocity.VelocityContext;
import org.eric.models.User;
import org.eric.services.UserService;

public class HomeController extends BaseController {

    protected final UserService userService;
    
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    public String getHomePage(User currentUser) {
        VelocityContext context = getHomePageData(currentUser);
        String page = renderView(context, "pages/home.vm");
        return page;
    }

    private VelocityContext getHomePageData(User currentUser) {
        VelocityContext context = new VelocityContext();

        context.put("user", currentUser);
        
        return context;
    }

}
