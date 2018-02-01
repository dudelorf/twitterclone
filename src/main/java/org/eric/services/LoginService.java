/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eric.services;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author EKinzel
 */
public class LoginService extends BaseService{
    
    public LoginService(BasicDataSource datasource){
        super(datasource);
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
