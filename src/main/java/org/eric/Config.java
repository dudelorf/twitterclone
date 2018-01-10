package org.eric;

import java.net.URL;
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
        String templateDir = getTemlateDirPath();
        config.props.put("template.dir", templateDir);

        instance = config;
    }

    private static void loadProperties(Config config){
        try{
            config.props.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties"));
        }catch(Exception exc){
            System.out.println(exc.getMessage());
            exc.printStackTrace();
            throw new RuntimeException("Unable to load properties file!", exc);
        }
    }

    private static String getTemlateDirPath(){
        URL resource = Thread.currentThread()
                                  .getContextClassLoader()
                                  .getResource("/");
        return resource.getPath();
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

    public String getTemplateDir(){
        return props.getProperty("template.dir");
    }
}