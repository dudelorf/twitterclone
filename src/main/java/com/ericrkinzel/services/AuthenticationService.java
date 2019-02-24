package com.ericrkinzel.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.dbcp.BasicDataSource;
import com.ericrkinzel.models.User;

/**
 * Handles all authentication and security related functionality
 */
public class AuthenticationService extends BaseService{
    
    protected UserService userService;
    
    public AuthenticationService(
    		BasicDataSource datasource, 
    		UserService userService
	){
        super(datasource);
        this.userService = userService;
    }

    /**
     * Encrypts a password
     * 
     * @param password password to encrypt
     * @param salt
     * @return encrypted password
     */
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
    
    /**
     * Generates a new salt for password
     * 
     * @return salt
     */
    public String generateSalt(){
        byte[] saltBytes = new byte[8];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(saltBytes);
        
        String asString = new BigInteger(1, saltBytes).toString(16);
        return asString;
    }
    
    /**
     * Generates a new token string
     * 
     * @return token
     */
    public String generateToken(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[30];
        random.nextBytes(bytes);
        String token = new BigInteger(1, bytes).toString(16);
        return token;
    }
    
    /**
     * Gets expiration time for token
     * 
     * @return token expiration
     */
    public Timestamp generateTokenExpiration(){
        Timestamp tokenExpiration = Timestamp.from(Instant.now().plus(24, ChronoUnit.HOURS));
        return tokenExpiration;
    }
    
    /**
     * Validates supplied login credentials
     * 
     * @param email supplied email
     * @param password supplied password
     * @return if credentials are valid
     */
    public boolean validateCredentials(String email, String password){
        User currentUser = userService.loadByEmail(email);
        String salt = currentUser.getSalt();
        return encryptPassword(password, salt).equals(currentUser.getPassword());
    }
    
    /**
     * Logs user into system
     * 
     * @param email username to log in
     * @return login token
     */
    public String loginUser(String email){
        User currentUser = userService.loadByEmail(email);
        String token = generateToken();
        Timestamp tokenExp = generateTokenExpiration();
        
        currentUser.setToken(token);
        currentUser.setTokenExpiration(tokenExp);
        
        userService.saveUser(currentUser);
        
        return token;
    }
    
    /**
     * Registers a new user
     * 
     * @param email new user email
     * @param username new user username
     * @param password new user password
     * @param firstname new user first name
     * @param lastname new user last name
     * @return success of save
     */
    public boolean registerUser(String email,
                                String username, 
                                String password,
                                String firstname,
                                String lastname){
        User newUser = new User();

        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setFirstname(firstname);
        newUser.setLastname(lastname);
        
        String salt = generateSalt();
        newUser.setSalt(salt);

        String encryptedPass = encryptPassword(password, salt);
        newUser.setPassword(encryptedPass);
        
        return userService.saveUser(newUser);
    }
    
    /**
     * Checks if supplied token is valid
     * 
     * @param token
     * @return if token is valid
     */
    public boolean validateToken(String token){
        boolean valid = false;
        
        User theUser = userService.loadByToken(token);
        Timestamp tokenExp = theUser.getTokenExpiration();
        if(tokenExp == null){
            valid = false;
        }else if(tokenExp.toInstant().isAfter(Instant.now())){
            valid = true;
        }
        
        return valid;
    }
    
    /**
     * Checks if username can be used for new registration
     * 
     * Avoids duplicate usernames
     * 
     * @param username
     * @return if username is valid for new registration
     */
    public boolean validateUsername(String username){
        User user = userService.loadByUsername(username);
        return user.getUsername().equals("");
    }
    
    /**
     * Checks if email can be used for new registration
     * 
     * Avoids duplicate emails
     * s
     * @param email
     * @return if email is valid for new registration
     */
    public boolean validateEmail(String email){
        User user = userService.loadByEmail(email);
        return user.getEmail().equals("");
    }
}
