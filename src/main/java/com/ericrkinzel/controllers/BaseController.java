package com.ericrkinzel.controllers;

import java.io.StringWriter;

import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Base class for all controllers
 * 
 * Shared functionality required by all other controllers
 */
public class BaseController {
    
	private ServletContext context;
	
	/**
	 * @param context current servlet context
	 */
	public BaseController(ServletContext context) {
		this.context = context;
	}
	
    /**
     * Renders a view
     * 
     * @param context data to render into template
     * @param templateFile path to template
     * @return rendered view
     */
    public String renderView(VelocityContext context, String templateFile){
    	context.put("_context", this.context.getContextPath());
    	
        StringWriter writer = new StringWriter();
        
        Template tpl = Velocity.getTemplate(templateFile);
        tpl.merge(context, writer);

        return writer.toString();
    }
}
