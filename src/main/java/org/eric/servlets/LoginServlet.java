package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.AuthenticationService;
import org.eric.controllers.LoginController;
import org.eric.services.UserService;

public class LoginServlet extends HttpServlet{

    static final long serialVersionUID = 1;
    
    protected LoginController getController(BasicDataSource datasource){
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        return new LoginController(authenticationService);
    }
    
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
     * Logs user in and sets credentials
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
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
            authCookie.setPath("/");
            authCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(authCookie);
            
            response.sendRedirect("/home");
        }else{            
            //Show login error
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();            
            out.print(controller.getLoginErrorPage());
        }
    }
}