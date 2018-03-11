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

    public User loadById(int id){
        ResultSetHandler<User> handler = new BeanHandler<>(User.class);

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
        ResultSetHandler<User> handler = new BeanHandler<>(User.class);

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
                           theUser.getToken_expiration(),
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
