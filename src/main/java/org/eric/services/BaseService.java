package org.eric.services;

import java.io.StringWriter;
import java.sql.Connection;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class BaseService {

    protected Connection conn;
    
    public BaseService(Connection conn){
        this.conn = conn;
    }

    /**
     * Renders the view
     * 
     * @param context data to render into template
     * @param templateFile path to template
     */
    public String renderView(VelocityContext context, String templateFile){
        StringWriter writer = new StringWriter();
        
        Template tpl = Velocity.getTemplate(templateFile);
        tpl.merge(context, writer);

        return writer.toString();
    }
}