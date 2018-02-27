package org.eric.app;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {

    private final Properties props = new Properties();

    private static Config instance;

    private Config(){}

    public static Config getInstance(){
        if(instance == null){
            loadInstance();
        }
        return instance;
    }

    private static void loadInstance(){
        Config config = new Config();
        
        //load properties file
        loadProperties(config);

        instance = config;
    }

    /**
     * Loads the applicaiton properties file
     * 
     * Looks for System property 'appenv' to know which file to load
     *  -Dappenv=production
     * 
     * @param config fresh config instace to populate
     */
    private static void loadProperties(Config config){
        try{
            String appenv = System.getProperty("appenv");
            String propertiesFile = "";

            //Load production properties
            if(appenv != null && appenv.equals("production")){
                propertiesFile = "production.properties";
            //Load dev properties
            }else{
                propertiesFile = "dev.properties";
            }

            config.props.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(propertiesFile));
                
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            exc.printStackTrace();
            throw new RuntimeException("Unable to load properties file!", exc);
        }
    }

    public String getDbDriver(){
        return props.getProperty("dbdriver");
    }

    public String getDbUrl(){
        return props.getProperty("dburl");
    }

    public String getDbUser(){
        return props.getProperty("dbuser");
    }

    public String getDbPass(){
        return props.getProperty("dbpass");
    }

    public int getInitialDbPoolSize(){
        return Integer.valueOf(props.getProperty("inital.dbpool.size"));
    }
    
    public List<String> getWhitelistRoutes(){
        String whitelist = props.getProperty("whitelist");
        return Arrays.asList(whitelist.split(","));
    }
}