package com.ericrkinzel.services;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import com.ericrkinzel.models.User;

/**
 * Manages user data
 */
public class UserService extends BaseService{
    
    public UserService(BasicDataSource datasource) {
        super(datasource);
    }
    
    /**
     * Gets user by email
     * 
     * @param email
     * @return user details
     */
    public User loadByEmail(String email){
        ResultSetHandler<User> handler = new BeanHandler<>(User.class);

        String sql = "SELECT * "
                   + "FROM users "
                   + "WHERE email = ? ";

        User theUser = query(handler, sql, email);
        if(theUser == null){
            return new User();
        }else{
            return theUser;
        }
    }
    
    /**
     * Gets user by username
     * 
     * @param username
     * @return user details
     */
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

    /**
     * Gets user by id
     * 
     * @param id
     * @return user details
     */
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
    
    /**
     * Gets user by access token
     * 
     * @param token
     * @return user details
     */
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
    
    /**
     * Saves user detals
     * 
     * @param theUser
     * @return success of save
     */
    public boolean saveUser(User theUser){
        if(theUser.getId() != -1){
            return updateUser(theUser);
        }else{
            return saveNewUser(theUser);
        }
    }
    
    /**
     * Updates user details
     * 
     * @param theUser
     * @return success of operation
     */
    protected boolean updateUser(User theUser){
        String sql = "UPDATE users SET "
                   + " email = ?, "
                   + " username = ?, "
                   + " firstname = ?, "
                   + " lastname = ?, "
                   + " password = ?, "
                   + " salt = ?, "
                   + " token = ?, "
                   + " tokenExpiration = ? "
                   + " WHERE id = ? ";
        
        return update(sql, theUser.getEmail(),
                           theUser.getUsername(),
                           theUser.getFirstname(),
                           theUser.getLastname(),
                           theUser.getPassword(),
                           theUser.getSalt(),
                           theUser.getToken(),
                           theUser.getTokenExpiration(),
                           theUser.getId()
                      ) != -1;
    }
    
    /**
     * Saves new user object
     * 
     * @param theUser
     * @return success of operation
     */
    protected boolean saveNewUser(User theUser){
        String sql = "INSERT INTO users "
                   + "( "
                   + " email, "
                   + " username, "
                   + " firstname, "
                   + " lastname, "
                   + " password, "
                   + " salt "
                   + ") " 
                   + " VALUES "
                   + "(?, ?, ?, ?, ?, ?)";
        
        return update(sql, theUser.getEmail(),
                           theUser.getUsername(),
                           theUser.getFirstname(),
                           theUser.getLastname(),
                           theUser.getPassword(),
                           theUser.getSalt()
                     ) != -1;
    }
}