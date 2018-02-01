package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.AuthenticationService;
import org.eric.services.LoginService;

public class LoginServlet extends HttpServlet{

    static final long serialVersionUID = 1;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        
        
        LoginService svc = new LoginService(datasource);

        out.print(svc.getLoginPage());
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
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");

        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");

        AuthenticationService svc = new AuthenticationService(datasource);
        if(svc.validateCredentials(username, password)){
            //login successful
            String token = svc.loginUser(username);
            Cookie authCookie = new Cookie("token", token);
            response.addCookie(authCookie);
            
            response.sendRedirect("/home");
        }else{            
            //Show login error
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            
            LoginService loginSvc = new LoginService(datasource);

            out.print(loginSvc.getLoginErrorPage());
        }
    }
}