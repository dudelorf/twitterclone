package com.ericrkinzel.controllers;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.models.User;
import com.ericrkinzel.services.AuthenticationService;
import com.ericrkinzel.services.UserService;

/**
 * Handles account routes
 */
public class AccountController extends BaseController{
    
    protected UserService userService;
    
    protected AuthenticationService authenticationService;
    
    public AccountController(
		UserService userService,
		AuthenticationService authenticationService,
		ServletContext context
	){
    	super(context);
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
    
    /**
     * Displays the account edit page
     *  
     * @param currentUser active user
     * @param message user notification message 
     * @param error reported errors
     * @return rendered view
     */
    public String getAccountEditPage(User currentUser, 
                                     String message,
                                     String error){
        
        VelocityContext ctx = new VelocityContext();
        
        ctx.put("email", currentUser.getEmail());
        ctx.put("username", currentUser.getUsername());
        ctx.put("firstname", currentUser.getFirstname());
        ctx.put("lastname", currentUser.getLastname());
        ctx.put("message", message);
        ctx.put("error", error);
        
        return renderView(ctx, "pages/accountEdit.vm");
    }
    
    /**
     * Saves user account information
     * 
     * @param currentUser current active user details
     * @param email new email
     * @param username new username
     * @param firstname new first name
     * @param lastname new last name
     * @param password new password
     * @return error message if applicable, null otherwise
     */
    public String updateAccount(User currentUser,
                                String email,
                                String username,
                                String firstname,
                                String lastname,
                                String password){
        
        if(!currentUser.getEmail().equals(email) &&
           !authenticationService.validateEmail(email)){
            return "Email already in use";
            
        }else{
           currentUser.setEmail(email);
        }
        
        if(!currentUser.getUsername().equals(username) &&
           !authenticationService.validateUsername(username)){
            return "Username already in use";
            
        }else{
            currentUser.setUsername(username);
        }
        
        currentUser.setFirstname(firstname);
        currentUser.setLastname(lastname);
        
        if(password.length() > 0){
            String newSalt = authenticationService.generateSalt();
            currentUser.setSalt(newSalt);
            
            String encryptedPw = authenticationService.encryptPassword(password, newSalt);
            currentUser.setPassword(encryptedPw);
        }
        
        if(userService.saveUser(currentUser)){
            return null;
        }else{
            return "Unable to update your account";
        }
        
    }
}
