package com.ericrkinzel.controllers;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.services.AuthenticationService;

/**
 * Handles login routes
 */
public class LoginController extends BaseController{
    
    private final AuthenticationService authenticationService;
    
    public LoginController(
		AuthenticationService authenticationService,
		ServletContext context
	){
        super(context);
        
        this.authenticationService = authenticationService;
    }
    
    /**
     * Validates login credentials
     * 
     * @param email supplied email
     * @param password supplied password
     * @return if credentials valid
     */
    public boolean validateLogin(String email, String password){
        return authenticationService.validateCredentials(email, password);
    }
    
    /**
     * Logs in the user
     * 
     * @param email user email
     * @return access token 
     */
    public String loginUser(String email){
        return authenticationService.loginUser(email);
    }
    
    /**
     * Renders the login page
     * 
     * @return rendered page
     */
    public String getLoginPage(){
        VelocityContext ctx = new VelocityContext();
        return renderView(ctx, "pages/login.vm");
    }
    
    /**
     * Renders the login page with errors
     * 
     * @return rendered page
     */
    public String getLoginErrorPage(){
        VelocityContext ctx = new VelocityContext();
        ctx.put("error", "Invalid login credentials.");
        return renderView(ctx, "pages/login.vm");
    }
}
