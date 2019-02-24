package com.ericrkinzel.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for logout routes
 */
public class LogoutServlet extends HttpServlet {
    
    static final long serialVersionUID = 1;

    /**
     * Logs out the user
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{

        //Attempt to clear token cookie
        Cookie[] cookies = request.getCookies();
        Cookie tokenCookie = null;
        if(cookies != null){
            for(Cookie c : cookies){
                if(c.getName().equals("token")){
                    tokenCookie = c;
                }
            }
        }

        if(tokenCookie != null){
        	tokenCookie.setPath(this.getServletContext().getContextPath());
            tokenCookie.setMaxAge(0);
            tokenCookie.setValue("");
            response.addCookie(tokenCookie);
        }
        
        response.setContentType("text/html");
        response.sendRedirect(this.getServletContext().getContextPath());
    }
}