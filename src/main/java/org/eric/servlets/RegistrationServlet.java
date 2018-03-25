package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.controllers.RegistrationController;
import org.eric.services.AuthenticationService;
import org.eric.services.UserService;

public class RegistrationServlet extends HttpServlet{
    
    static final long serialVersionUID = 1;
    
    protected RegistrationController getController(BasicDataSource datasource){
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        return new RegistrationController(authenticationService);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        RegistrationController controller = getController(datasource);
        
        out.print(controller.showRegistrationPage());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException{
       
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //Gather registration form data
        String email = (String) request.getParameter("email");
        String username = (String) request.getParameter("username");
        String password = (String) request.getParameter("password");
        String firstname = (String) request.getParameter("firstname");
        String lastname = (String) request.getParameter("lastname");
        
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                            .getAttribute("datasource");
        RegistrationController controller = getController(datasource);
        
        //Attempt to register user
        String error = controller.processRegistration(email,
                                                      username, 
                                                      password,
                                                      firstname,
                                                      lastname);
        if(error == null){
            request.getRequestDispatcher("/login").forward(request, response);
        }else{
            out.print(controller.showRegistrationErrors(error));
        }
        
    }
}
