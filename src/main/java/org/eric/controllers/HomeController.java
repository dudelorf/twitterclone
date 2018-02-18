package org.eric.controllers;

import org.apache.velocity.VelocityContext;
import org.eric.services.UserService;

public class HomeController extends BaseController {

    protected final UserService userService;
    
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    public String getHomePage(int userId) {
        VelocityContext context = getHomePageData(userId);
        String page = renderView(context, "pages/home.vm");
        return page;
    }

    private VelocityContext getHomePageData(int userId) {
        VelocityContext context = new VelocityContext();

        //User currentUser = getCurrentUser(userId);
        //context.put("user", currentUser);
        
        return context;
    }

}
