package com.ericrkinzel.services;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * Base for all service classes
 * 
 * Provides common functionality used by all services
 */
public class BaseService {

    protected BasicDataSource datasource;
    
    public BaseService(BasicDataSource datasource){
        this.datasource = datasource;
    }
    
    /**
     * Returns connection from datasource
     * 
     * @return connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException{
        
        return datasource.getConnection();
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
        try{
            QueryRunner runner = new QueryRunner(datasource);
            return runner.query(sql, handler, params);
        }catch(SQLException exc){
            exc.printStackTrace();
            return null;
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
        try{
            QueryRunner runner = new QueryRunner(datasource);
            return runner.update(sql, params);
        }catch(SQLException exc){
            exc.printStackTrace();
            return -1;
        }
    }
}