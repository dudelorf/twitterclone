/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eric.services;

import java.sql.Connection;
import org.apache.velocity.VelocityContext;

/**
 *
 * @author EKinzel
 */
public class LoginService extends BaseService{
    
    public LoginService(Connection conn){
        super(conn);
    }
    
    public String getLoginPage(){
        VelocityContext ctx = new VelocityContext();
        return renderView(ctx, "pages/login.vm");
    }
}
