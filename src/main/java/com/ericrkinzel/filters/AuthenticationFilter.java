package com.ericrkinzel.filters;

import com.ericrkinzel.app.Config;
import com.ericrkinzel.models.User;
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
import com.ericrkinzel.services.AuthenticationService;
import com.ericrkinzel.services.UserService;

/**
 * Master authentication filter
 * 
 * Checks for the presence of token cookie.
 */
public class AuthenticationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Applies security to all requests
     * 
     * Resources and whitelisted routes are not subject to security.
     * If route is subject to security filter ensures a valid token is present
     * before proceeding. Failure to authenticate will clear existing token
     * and redirect to login. Successful authentication will set the current
     * user details as a request attribute.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        
        List<String> whitelist = Config.getInstance().getWhitelistRoutes();
        
        //Get route information
        String route = httpReq.getRequestURI()
        	// remove context from route
    		.replace(httpReq.getServletContext().getContextPath(), "")
    		//remove leading '/'
	        .substring(1);
        
        //Don't worry about web resources
        if(route.startsWith("resources")){
            chain.doFilter(request, response);
            return;
        }

        //Instantiate authentication service to validate token
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                        .getAttribute("datasource");
        UserService userService = new UserService(datasource);
        
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
     
        if(!route.equals("") && !whitelist.contains(route)){
            
        	// Check for access token
            Cookie[] cookies = httpReq.getCookies();
            Cookie tokenCookie = null;
            if(cookies != null){
                for(Cookie c : cookies){
                    if(c.getName().equals("token")){
                        tokenCookie = c;
                        break;
                    }
                }
            }

            if(tokenCookie == null){
                tokenCookie = new Cookie("token", "");
                tokenCookie.setPath(httpReq.getServletContext().getContextPath());
                httpResp.addCookie(tokenCookie);
            }

            if(!authenticationService.validateToken(tokenCookie.getValue())){
            	// Token not valid, log out
                tokenCookie.setMaxAge(0);
                httpResp.addCookie(tokenCookie);
                httpResp.sendRedirect(httpReq.getServletContext().getContextPath());
                return;
            }else{
                // Store current user
                User currentUser = userService.loadByToken(tokenCookie.getValue());
                httpReq.setAttribute("user", currentUser);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}