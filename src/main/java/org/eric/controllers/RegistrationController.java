package org.eric.controllers;

import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import org.eric.services.AuthenticationService;

public class RegistrationController extends BaseController{
    
    protected final AuthenticationService authenticationService;
    public RegistrationController(AuthenticationService authenticationService){
        super();
        
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
    
    public String processRegistration(String username, String password){
        Map<String, String> map = new HashMap<>();
        
        if(!authenticationService.validateUsername(username)){
            return "This email has already been registered.";
            
        }else if(!authenticationService.registerUser(username, password)){
            return "There was an error registereing. Please try again.";
        }
        
        return null;
    }
}
