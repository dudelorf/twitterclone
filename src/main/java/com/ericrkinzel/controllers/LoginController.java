/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericrkinzel.controllers;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.services.AuthenticationService;

/**
 *
 * @author EKinzel
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
    
    public boolean validateLogin(String email, String password){
        return authenticationService.validateCredentials(email, password);
    }
    
    public String loginUser(String email){
        return authenticationService.loginUser(email);
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
