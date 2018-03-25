package org.eric.services;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.eric.models.User;

public class UserService extends BaseService{
    
    public UserService(BasicDataSource datasource) {
        super(datasource);
    }
    
    public User loadByUsername(String username){
        ResultSetHandler<User> handler = new UserBeanHandler();

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

    public User loadById(int id){
        ResultSetHandler<User> handler = new UserBeanHandler();

        String sql = "SELECT * "
                   + "FROM users "
                   + "WHERE id = ? ";

        User theUser = query(handler, sql, id);
        if(theUser == null){
            return new User();
        }else{
            return theUser;
        }
    }
    
    public User loadByToken(String token){
        ResultSetHandler<User> handler = new UserBeanHandler();

        String sql = "SELECT * "
                   + "FROM users "
                   + "WHERE token = ? ";

        User theUser = query(handler, sql, token);
        if(theUser == null){
            return new User();
        }else{
            return theUser;
        }
    }
    
    public boolean saveUser(User theUser){
        if(theUser.getId() != -1){
            return updateUser(theUser);
        }else{
            return saveNewUser(theUser);
        }
    }
    
    protected boolean updateUser(User theUser){
        String sql = "UPDATE users SET "
                   + " username = ?, "
                   + " firstname = ?, "
                   + " lastname = ?, "
                   + " password = ?, "
                   + " salt = ?, "
                   + " token = ?, "
                   + " token_expiration = ? "
                   + " WHERE id = ? ";
        
        return update(sql, theUser.getUsername(),
                           theUser.getFirstname(),
                           theUser.getLastname(),
                           theUser.getPassword(),
                           theUser.getSalt(),
                           theUser.getToken(),
                           theUser.getTokenExpiration(),
                           theUser.getId()
                      ) != -1;
    }
    
    protected boolean saveNewUser(User theUser){
        String sql = "INSERT INTO users "
                   + "( "
                   + " username, "
                   + " firstname, "
                   + " lastname, "
                   + " password, "
                   + " salt "
                   + ") " 
                   + " VALUES "
                   + "(?, ?, ?, ?, ?)";
        
        return update(sql, theUser.getUsername(),
                           theUser.getFirstname(),
                           theUser.getLastname(),
                           theUser.getPassword(),
                           theUser.getSalt()
                     ) != -1;
    }
}

/**
 * Bean handler for User models
 */
class UserBeanHandler extends BeanHandler<User>{
    
    public UserBeanHandler() {
        super(User.class,
              new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
    }

    /**
     * Provides mapping from database fields to User model fields
     * 
     * @return field mappings
     */
    public static Map<String, String> getColumnsToFieldsMap(){
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("token_expiration", "tokenExpiration");
        return fieldsMap;
    }
        
}