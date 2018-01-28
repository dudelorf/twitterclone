package org.eric.services;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.VelocityContext;

public class RegistrationService extends BaseService{
    
    public RegistrationService(BasicDataSource datasource){
        super(datasource);
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
        AuthenticationService svc = new AuthenticationService(datasource);
        
        if(!svc.validateUsername(username)){
            return "This email has already been registered.";
            
        }else if(!svc.registerUser(username, password)){
            return "There was an error registereing. Please try again.";
        }
        
        return null;
    }
}
