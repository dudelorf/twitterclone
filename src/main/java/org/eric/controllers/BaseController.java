/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eric.controllers;

import java.io.StringWriter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author ericr
 */
public class BaseController {
    
    /**
     * Renders the view
     * 
     * @param context data to render into template
     * @param templateFile path to template
     * @return rendered view
     */
    public String renderView(VelocityContext context, String templateFile){
        StringWriter writer = new StringWriter();
        
        Template tpl = Velocity.getTemplate(templateFile);
        tpl.merge(context, writer);

        return writer.toString();
    }
    
}
