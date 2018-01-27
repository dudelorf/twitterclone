package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.LoginService;
import org.eric.services.RegistrationService;

public class RegistrationServlet extends HttpServlet{
    
    static final long serialVersionUID = 1;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        
        RegistrationService svc = new RegistrationService(datasource);
        out.print(svc.showRegistrationPage());
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //Gather registration form data
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");

        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        RegistrationService svc = new RegistrationService(datasource);
        
        Map<String, String> errors = svc.processRegistration(username, password);
        if(errors.isEmpty()){
            request.getRequestDispatcher("/login").forward(request, response);
        }else{
            out.print(svc.showRegistrationErrors(errors));
        }
        
    }
}
