package com.ericrkinzel.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import com.ericrkinzel.services.AuthenticationService;
import com.ericrkinzel.controllers.LoginController;
import com.ericrkinzel.services.UserService;

/**
 * Servlet for login routes
 */
public class LoginServlet extends HttpServlet{

    static final long serialVersionUID = 1;
    
    protected LoginController getController(BasicDataSource datasource){
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        return new LoginController(authenticationService, this.getServletContext());
    }
    
    /**
     * Gets login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        LoginController controller = getController(datasource);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(controller.getLoginPage());
    }
    
    /**
     * Master login route
     * 
     * Logs user in and sets credentials on request 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        
        //Gather registration form data
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");

        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        LoginController controller = getController(datasource);
        
        if(controller.validateLogin(email, password)){
            //login successful
            String token = controller.loginUser(email);
            Cookie authCookie = new Cookie("token", token);
            authCookie.setPath(this.getServletContext().getContextPath());
            authCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(authCookie);
            
            response.sendRedirect(this.getServletContext().getContextPath() + "/home");
        }else{            
            //Show login error
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();            
            out.print(controller.getLoginErrorPage());
        }
    }
}