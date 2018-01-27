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
    
    public String showRegistrationErrors(Map<String, String> errors){
        return "error page";
    }
    
    public Map<String, String> processRegistration(String username, String password){
        Map<String, String> map = new HashMap<>();
        
        return map;
    }
}
