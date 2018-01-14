package org.eric.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.eric.models.User;

public class AuthenticationService extends BaseService{
    
    public AuthenticationService(BasicDataSource datasource){
        super(datasource);
    }

    public String encryptPassword(String password, String salt) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] hashBytes = instance.digest((password + salt).getBytes());
            String hash = new BigInteger(1, hashBytes).toString(16);
            return hash;
            
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
    
    public String generateSalt(){
        byte[] saltBytes = new byte[12];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(saltBytes);
        
        String asString = new BigInteger(1, saltBytes).toString(16);
        return asString;
    }
    
    public boolean validateCredentials(String username, String password){
        User currentUser = getUserDetails(username);
        String salt = currentUser.getSalt();
        if(encryptPassword(password, salt).equals(currentUser.getPassword())){
            return true;
        }else{
            return false;
        }
    }
    
    protected User getUserDetails(String username){
        Connection conn = null;
        
        try{
            conn = datasource.getConnection();
            QueryRunner runner = new QueryRunner();
            ResultSetHandler<User> handler = new BeanHandler<>(User.class);
            
            String sql = "SELECT * "
                       + "FROM users "
                       + "WHERE username = ? ";
            
            User theUser = runner.query(sql, handler, username);
            return theUser;
            
        }catch(SQLException exc){
            return new User();
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
}
