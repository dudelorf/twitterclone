package org.eric.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationService {

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
}
