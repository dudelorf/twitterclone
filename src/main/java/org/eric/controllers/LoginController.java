/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eric.controllers;

import org.apache.velocity.VelocityContext;
import org.eric.services.AuthenticationService;

/**
 *
 * @author EKinzel
 */
public class LoginController extends BaseController{
    
    private final AuthenticationService authenticationService;
    
    public LoginController(AuthenticationService authenticationService){
        super();
        
        this.authenticationService = authenticationService;
    }
    
    public boolean validateLogin(String username, String password){
        return authenticationService.validateCredentials(username, password);
    }
    
    public String loginUser(String username){
        return authenticationService.loginUser(username);
    }
    
    public String getLoginPage(){
        VelocityContext ctx = new VelocityContext();
        return renderView(ctx, "pages/login.vm");
    }
    
    public String getLoginErrorPage(){
        VelocityContext ctx = new VelocityContext();
        ctx.put("error", "Invalid login credentials.");
        return renderView(ctx, "pages/login.vm");
    }
}
