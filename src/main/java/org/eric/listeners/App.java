package org.eric.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.app.Velocity;
import org.eric.Config;

public class App implements ServletContextListener {
    
    /**
     * App initialization
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        
        Config config = Config.getInstance();

        initVelocity(config);
        BasicDataSource dbpool = initDatasource(config);

        ctx.setAttribute("datasource", dbpool);
    }
    
    /**
     * Loads velocity singleton
     */
    private void initVelocity(Config config){
        String templateDir = config.getTemplateDir();
        Velocity.setProperty("file.resource.loader.path", templateDir);
        Velocity.init();
    }
    
    /**
     * Initializes and configures database connection pool
     */
    private BasicDataSource initDatasource(Config config){
        BasicDataSource dbpool = new BasicDataSource();
        dbpool.setDriverClassName(config.getDbDriver());
        dbpool.setUrl(config.getDbUrl());
        dbpool.setUsername(config.getDbUser());
        dbpool.setPassword(config.getDbPass());
        dbpool.setInitialSize(config.getInitialDbPoolSize());
        return dbpool;
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}