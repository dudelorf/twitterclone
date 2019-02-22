package com.ericrkinzel.controllers;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.services.AuthenticationService;

public class RegistrationController extends BaseController{
    
    protected final AuthenticationService authenticationService;
    
    public RegistrationController(
		AuthenticationService authenticationService,
		ServletContext context
	){
        super(context);
        
        this.authenticationService = authenticationService;
    }
    
    public String showRegistrationPage(){
        VelocityContext ctx = new VelocityContext();
        return renderView(ctx, "pages/registration.vm");
    }
    
    public String showRegistrationErrors(String error){
        VelocityContext ctx = new VelocityContext();
        ctx.put("error", error);
        return renderView(ctx, "pages/registration.vm");
    }
    
    public String processRegistration(String email,
                                      String username,
                                      String password,
                                      String firstname,
                                      String lastname){
        if(!authenticationService.validateUsername(username)){
            return "This email has already been registered.";
            
        }else if(!authenticationService.validateEmail(email)){
            return "This username has already been registered.";
            
        }else if(!authenticationService.registerUser(email,
                                                     username, 
                                                     password,
                                                     firstname,
                                                     lastname)){
            return "There was an error registering. Please try again.";
        }
        
        return null;
    }
}
