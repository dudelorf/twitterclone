package org.eric.services;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.eric.models.User;

public class UserService extends BaseService{
    
    public UserService(BasicDataSource datasource) {
        super(datasource);
    }
    
    public User loadByUsername(String username){
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
                + " password = ?, "
                + " salt = ?, "
                + " token = ?, "
                + " token_expiration = ? "
                + " WHERE id = ? ";
        
        if(update(sql, theUser.getUsername(),
                       theUser.getPassword(), 
                       theUser.getSalt(),
                       theUser.getToken(),
                       theUser.getToken_expiration(),
                       theUser.getId()) != -1){
            return true;
        }else{
            return false;
        }
    }
    
    protected boolean saveNewUser(User theUser){
        String sql = "INSERT INTO users"
                   + "(username,"
                   + "password,"
                   + "salt)"
                   + "VALUES"
                   + "(?, ?, ?)";
        
        if(update(sql, theUser.getUsername(),
                       theUser.getPassword(), 
                       theUser.getSalt()) != -1){
            return true;
        }else{
            return false;
        }
    }
}
