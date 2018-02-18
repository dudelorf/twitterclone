package org.eric.filters;

import org.eric.app.Config;
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
        
        //Instantiate authentication service
        BasicDataSource datasource = (BasicDataSource) request.getServletContext()
                                        .getAttribute("datasource");
        UserService userService = 
                new UserService(datasource);
        AuthenticationService authenticationService = 
                new AuthenticationService(datasource, userService);
        
        if(route.equals("") || whitelist.contains(route)){
            
            chain.doFilter(request, response);
        }else{
            String token = "";
            Cookie[] cookies = httpReq.getCookies();
            if(cookies == null){
                token = "";
            }else{
                for(Cookie c : cookies){
                    if(c.getName().equals("token")){
                        token = c.getValue();
                    }
                }
            }
            //Token valid
            if(authenticationService.validateToken(token)){
                chain.doFilter(request, response);
                
            //token not valid, log out
            }else{
                Cookie expiredToken = new Cookie("token", "");
                expiredToken.setMaxAge(-1);
                httpResp.addCookie(expiredToken);
                httpResp.sendRedirect("/");
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {}

}