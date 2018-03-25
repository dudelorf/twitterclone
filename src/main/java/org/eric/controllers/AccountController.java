/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eric.controllers;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.VelocityContext;
import org.eric.models.User;
import org.eric.services.UserService;

/**
 *
 * @author ericr
 */
public class AccountController extends BaseController{
    
    protected UserService userService;
    
    public AccountController(UserService userService){
        this.userService = userService;    
    }
    
    public String getAccountEditPage(User currentUser){
        VelocityContext ctx = new VelocityContext();
        
        ctx.put("email", currentUser.getEmail());
        ctx.put("username", currentUser.getUsername());
        ctx.put("firstname", currentUser.getFirstname());
        ctx.put("lastname", currentUser.getLastname());
        
        return renderView(ctx, "pages/accountEdit.vm");
    }
}
