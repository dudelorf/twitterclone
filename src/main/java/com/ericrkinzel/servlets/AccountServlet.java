package com.ericrkinzel.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import com.ericrkinzel.controllers.AccountController;
import com.ericrkinzel.models.User;
import com.ericrkinzel.services.AuthenticationService;
import com.ericrkinzel.services.UserService;

/**
 * Servlet for account route
 */
public class AccountServlet extends HttpServlet{
    
    private static final long serialVersionUID = 1L;

	protected AccountController getController(BasicDataSource datasource){
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        return new AccountController(userService, authenticationService, this.getServletContext());
    }
    
	/**
	 * Gets account details page
	 */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        
        AccountController accountController = getController(datasource);
        
        User currentUser = (User) request.getAttribute("user");
        String message = (String) request.getAttribute("message");
        if(message == null){
            message = "";
        }
        String error = (String) request.getAttribute("error");
        if(error == null){
            error = "";
        }
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.print(accountController.getAccountEditPage(currentUser,
                                                       message,
                                                       error));
    }
    
    /**
     * Handles account details form
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        
        AccountController accountController = getController(datasource);
        
        User currentUser = (User) request.getAttribute("user");
        
        String email = (String) request.getParameter("email");
        String username = (String) request.getParameter("username");
        String firstname = (String) request.getParameter("firstname");
        String lastname = (String) request.getParameter("lastname");
        String password = (String) request.getParameter("password");
        
        String error = accountController.updateAccount(currentUser,
                                                       email,
                                                       username,
                                                       firstname,
                                                       lastname,
                                                       password);
        
        if(error != null){
            request.setAttribute("error", error);
        }else{
            request.setAttribute("message", "Account updated successfully");
        }
        
        doGet(request, response);
    }
}
