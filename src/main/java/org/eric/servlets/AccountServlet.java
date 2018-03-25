package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.controllers.AccountController;
import org.eric.models.User;
import org.eric.services.UserService;

/**
 *
 * @author ericr
 */
public class AccountServlet extends HttpServlet{
    
    protected AccountController getController(BasicDataSource datasource){
        UserService userService = new UserService(datasource);
        
        return new AccountController(userService);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        
        AccountController accountController = getController(datasource);
        User currentUser = (User) request.getAttribute("user");
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        out.print(accountController.getAccountEditPage(currentUser));
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
    }
}
