package org.eric.filters;

import org.eric.app.Config;
import org.eric.models.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import org.eric.services.AuthenticationService;
import org.eric.services.UserService;

/**
 * Master authentication filter
 * 
 * Checks for the presence of token cookie.
 */
public class AuthenticationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        //Get route information
        List<String> whitelist = Config.getInstance().getWhitelistRoutes();
        String route = httpReq.getRequestURI().substring(1); //remove leading '/'
        
        //Don't worry about webapp resources
        if(route.startsWith("resources")){
            chain.doFilter(request, response);
            return;
        }

        //Instantiate authentication service
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                        .getAttribute("datasource");
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        if(!route.equals("") && !whitelist.contains(route)){
            
            Cookie[] cookies = httpReq.getCookies();
            Cookie tokenCookie = null;
            if(cookies != null){
                for(Cookie c : cookies){
                    if(c.getName().equals("token")){
                        tokenCookie = c;
                    }
                }
            }

            if(tokenCookie == null){
                tokenCookie = new Cookie("token", "");
                tokenCookie.setPath("/");
                httpResp.addCookie(tokenCookie);
            }

            //token not valid, log out
            if(!authenticationService.validateToken(tokenCookie.getValue())){
                tokenCookie.setMaxAge(0);
                httpResp.addCookie(tokenCookie);
                httpResp.sendRedirect("/");
                return;
            }else{
                //store current user
                User currentUser = userService.loadByToken(tokenCookie.getValue());
                httpReq.setAttribute("user", currentUser);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}