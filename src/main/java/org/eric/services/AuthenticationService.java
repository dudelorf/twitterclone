package org.eric.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.dbcp.BasicDataSource;
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
        byte[] saltBytes = new byte[8];
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
    
    public Timestamp generateTokenExpiration(){
        Timestamp tokenExpiration = Timestamp.from(Instant.now().plus(24, ChronoUnit.HOURS));
        return tokenExpiration;
    }
    
    public boolean validateCredentials(String username, String password){
        UserService svc = new UserService(datasource);
        User currentUser = svc.loadByUsername(username);
        String salt = currentUser.getSalt();
        return encryptPassword(password, salt).equals(currentUser.getPassword());
    }
    
    public String loginUser(String username){
        UserService svc = new UserService(datasource);
        
        User currentUser = svc.loadByUsername(username);
        String token = generateToken();
        Timestamp tokenExp = generateTokenExpiration();
        
        currentUser.setToken(token);
        currentUser.setToken_expiration(tokenExp);
        
        svc.saveUser(currentUser);
        
        return token;
    }
    
    public boolean registerUser(String username, String password){
        String salt = generateSalt();
        String encryptedPass = encryptPassword(password, salt);
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setSalt(salt);
        newUser.setPassword(encryptedPass);
        
        UserService svc = new UserService(datasource);
        return svc.saveUser(newUser);
    }
    
    public boolean validateToken(String token){
        boolean valid = false;
        
        return valid;
    }
    
    public boolean validateUsername(String username){
        UserService svc = new UserService(datasource);
        if(svc.loadByUsername(username).getUsername().equals("")){
            return true;
        }else{
            return false;
        }
    }
}
