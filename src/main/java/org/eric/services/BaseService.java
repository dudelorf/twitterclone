package org.eric.services;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;


public class BaseService {

    protected BasicDataSource datasource;
    
    public BaseService(BasicDataSource datasource){
        this.datasource = datasource;
    }
    
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