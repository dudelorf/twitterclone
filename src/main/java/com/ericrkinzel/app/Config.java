package com.ericrkinzel.app;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Singleton class holding application wide settings
 */
public class Config {

	/**
	 * Backing settings storage
	 */
    private final Properties props = new Properties();

    /**
     * Singleton
     */
    private static Config instance;

    /**
     * Private constructor to force static factory
     */
    private Config(){}

    /**
     * Static factory returning config instance
     * 
     * @return config instance
     */
    public static Config getInstance(){
    	// load instance the first time accessed
        if(instance == null){
            loadInstance();
        }
        return instance;
    }

    /**
     * Creates singleton
     */
    private static void loadInstance(){
        Config config = new Config();
        
        //load properties file
        loadProperties(config);

        instance = config;
    }

    /**
     * Loads the application properties file
     * 
     * @param config fresh config instace to populate
     */
    private static void loadProperties(Config config){
        try{
            
            String propertiesFile = "application.properties";

            config.props.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(propertiesFile));
                
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            exc.printStackTrace();
            throw new RuntimeException("Unable to load properties file!", exc);
        }
    }

    /** config properties **/
    
    /**
     * @return Fully qualified database drive class name
     */
    public String getDbDriver(){
        return props.getProperty("dbdriver");
    }

    /**
     * @return Database connection url
     */
    public String getDbUrl(){
        return props.getProperty("dburl");
    }

    /**
     * @return database connection username
     */
    public String getDbUser(){
        return props.getProperty("dbuser");
    }

    /**
     * @return database connection password
     */
    public String getDbPass(){
        return props.getProperty("dbpass");
    }

    public int getInitialDbPoolSize(){
        return Integer.valueOf(props.getProperty("inital.dbpool.size"));
    }
    
    /**
     * Whitelisted routes are publicly available and not subject to security
     * 
     * @return configured whitelisted routes
     */
    public List<String> getWhitelistRoutes(){
        String whitelist = props.getProperty("whitelist");
        return Arrays.asList(whitelist.split(","));
    }
}