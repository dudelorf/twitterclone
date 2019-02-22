/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericrkinzel.controllers;

import java.io.StringWriter;

import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author ericr
 */
public class BaseController {
    
	private ServletContext context;
	
	public BaseController(ServletContext context) {
		this.context = context;
	}
	
    /**
     * Renders the view
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
