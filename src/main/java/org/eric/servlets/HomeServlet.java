package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.eric.controllers.HomeController;
import org.eric.models.User;
import org.eric.services.PostService;
import org.eric.services.UserService;

public class HomeServlet extends HttpServlet {
    
    static final long serialVersionUID = 1;

    protected HomeController getController(BasicDataSource datasource){
        UserService userService = new UserService(datasource);
        PostService postService = new PostService(datasource);

        return new HomeController(userService, postService);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        response.setContentType("text/html");
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");                                
        
        HomeController controller = getController(datasource);
        User currentUser = (User) request.getAttribute("user");

        PrintWriter out = response.getWriter();
        out.print(controller.getHomePage(currentUser));
    }

}