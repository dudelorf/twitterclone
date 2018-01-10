package org.eric.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.HomeService;

public class HomeServlet extends HttpServlet {
    
    static final long serialVersionUID = 1;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        response.setContentType("text/html");
        BasicDataSource datasource = (BasicDataSource) getServletContext()
                                        .getAttribute("datasource");
        final PrintWriter out = response.getWriter();
                                        
        try {
            Connection conn = datasource.getConnection();
            HomeService svc = new HomeService(conn);

            out.print(svc.getHomePage(1));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}