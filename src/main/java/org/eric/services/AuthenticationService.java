package org.eric.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import javafx.util.Duration;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
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
    
    public String generateToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[30];
        random.nextBytes(bytes);
        String token = new BigInteger(1, bytes).toString(16);
        return token;
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
    
    public String setToken(String username){
        User theUser = getUserDetails(username);
        String token = generateToken();
        Timestamp tokenExpiration = Timestamp.from(Instant.now().plus(24, ChronoUnit.HOURS));
        
        String sql = "UPDATE users SET "
                   + "token = ?, "
                   + "token_expiration = ? "
                   + "WHERE id = ? ";
        
        if(update(sql, token, tokenExpiration, theUser.getId()) > 0){
            return token;
        }else{
            return "";
        }
    }
    
    public boolean validateToken(String token){
        boolean valid = false;
        
        String sql = "SELECT token_expiration "
                   + "FROM users "
                   + "WHERE token = ?";
            
        Map<String, Object> result = query(new MapHandler(), sql, token);
            
        if(!result.isEmpty()){
            Timestamp expiration = (Timestamp) result.get("token_expiration");
            if(expiration.toInstant().isBefore(Instant.now())){
                updateTokenExpiration(token);
                valid = true;
            }
        }
        
        return valid;
    }
    
    protected void updateTokenExpiration(String token){
        String sql = "UPDATE users "
                   + "SET token_expiration = ? "
                   + "WHERE token = ? ";

        Instant expiration = Instant.now().plus(24, ChronoUnit.HOURS);
        Timestamp tokenExpiration = Timestamp.from(expiration);
        update(sql, tokenExpiration, token);
    }
    
    protected User getUserDetails(String username){

        ResultSetHandler<User> handler = new BeanHandler<>(User.class);

        String sql = "SELECT * "
                   + "FROM users "
                   + "WHERE username = ? ";

        User theUser = query(handler, sql, username);
        if(theUser == null){
            return new User();
        }else{
            return theUser;
        }
    }
}
