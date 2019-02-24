package com.ericrkinzel.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import com.ericrkinzel.controllers.NewPostController;
import com.ericrkinzel.models.User;
import com.ericrkinzel.services.PostService;

/**
 * Servlet for new post routes
 */
public class NewPostServlet extends HttpServlet{

    static final long serialVersionUID = 1L;

    /**
     * Gets controller for newPost pages
     */
    protected NewPostController getController(BasicDataSource datasource){

        PostService postService = new PostService(datasource);

        return new NewPostController(postService, this.getServletContext());
    }
    
    /**
     * Gets the new post form
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
        .getAttribute("datasource");
        
        NewPostController controller = getController(datasource);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(controller.getNewPostForm());
    }

    /**
     * Process a new post
     * 
     * Any errors that occurred during processing are shown on form
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");

        NewPostController controller = getController(datasource);

        User currentUser = (User) request.getAttribute("user");

        int userId = currentUser.getId();
        String userName = currentUser.getUsername();
        String postBody = request.getParameter("bodytext");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String error = controller.processPostForm(postBody, userId, userName);
        if(error != null){
            out.print(controller.showPostSaveErrors(error));
        }else{
            response.sendRedirect(this.getServletContext().getContextPath() + "/home");
        }
    }
}