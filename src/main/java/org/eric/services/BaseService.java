package org.eric.services;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class BaseService {

    protected BasicDataSource datasource;
    
    public BaseService(BasicDataSource datasource){
        this.datasource = datasource;
    }
    
    public Connection getConnection() throws SQLException{
        
        return datasource.getConnection();
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
    
    /**
     * Executes the query
     * 
     * Results are specified by the supplied result set handler
     * 
     * @param <T> type of result
     * @param handler processes results
     * @param sql query
     * @param params parameters for query
     * @return results
     */
    public <T> T query(ResultSetHandler<T> handler, String sql, Object ... params){
        Connection conn = null;
        
        try{
            conn = datasource.getConnection();
            QueryRunner runner = new QueryRunner();
            
            return runner.query(conn, sql, params, handler);
        }catch(SQLException exc){
            return null;
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
    
    /**
     * Executes the UPDATE, INSERT or DELETE statement
     * 
     * @param sql query
     * @param params parameters for query
     * @return number of affected rows
     */
    public int update(String sql, Object ... params){
        Connection conn = null;
        
        try{
            conn = datasource.getConnection();
            QueryRunner runner = new QueryRunner();
            
            return runner.update(conn, sql, params);
        }catch(SQLException exc){
            return -1;
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
}