package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.RegistrationService;

public class RegistrationServlet extends HttpServlet{
    
    static final long serialVersionUID = 1;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        
        RegistrationService svc = new RegistrationService(datasource);
        out.print(svc.showRegistrationPage());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException{
       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //Gather registration form data
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");

        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        RegistrationService svc = new RegistrationService(datasource);
        
        //Attempt to register user
        String error = svc.processRegistration(username, password);
        if(error == null){
            System.out.println("saved");
            request.getRequestDispatcher("/login").forward(request, response);
        }else{
            out.print(svc.showRegistrationErrors(error));
        }
        
    }
}
