package org.eric.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbcp.BasicDataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.velocity.VelocityContext;
import org.eric.models.User;

public class HomeService extends BaseService {

    public HomeService(BasicDataSource datasource) {
        super(datasource);
    }

    public String getHomePage(int userId) {
        VelocityContext context = getHomePageData(userId);
        String page = renderView(context, "pages/home.vm");



        return page;
    }

    private VelocityContext getHomePageData(int userId) {
        VelocityContext context = new VelocityContext();

        //User currentUser = getCurrentUser(userId);
        //context.put("user", currentUser);
        
        return context;
    }

    private User getCurrentUser(int userId) {
        Connection conn = null;
        QueryRunner runner = new QueryRunner();
        BeanListHandler<User> handler = new BeanListHandler<>(User.class);

        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            conn = getConnection();
            List<User> users = runner.query(conn, sql, handler, userId);
            return users.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }

}
