package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.LoginService;

public class LoginServlet extends HttpServlet{

    static final long serialVersionUID = 1;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        response.setContentType("text/html");
        
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        
        PrintWriter out = response.getWriter();
        LoginService svc = new LoginService(datasource);

        out.print(svc.getLoginPage());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        
        PrintWriter out = response.getWriter();
        
        out.print("registration success");
    }
}