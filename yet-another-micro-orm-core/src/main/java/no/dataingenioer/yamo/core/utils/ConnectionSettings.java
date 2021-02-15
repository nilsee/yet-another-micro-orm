package no.dataingenioer.yamo.core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionSettings {

    private String name;
    private String url;
    private String database;
    private String driver;
    private String username;
    private String password;

    protected ConnectionSettings() {
    }

    public ConnectionSettings(String name, String url, String database, String driver, String username, String password) {

        this.name = name;
        this.url = url;
        this.database = database;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public static ConnectionSettings getConnectionSettings(String connectionSettingsFileName) throws IOException {

        Properties properties =  readPorperties(connectionSettingsFileName);

        return new ConnectionSettings(connectionSettingsFileName,
                properties.getProperty("yamo.url"),
                properties.getProperty("yamo.database"),
                properties.getProperty("yamo.driver"),
                properties.getProperty("yamo.username"),
                properties.getProperty("yamo.password"));
    }

    private static Properties readPorperties(String fileName) throws IOException {

        Properties prop = new Properties();
        InputStream is = new FileInputStream(fileName);
        prop.load(is);
        return prop;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getUrlWithDatabase() {
        String connectionString = getUrl();
        if(connectionString.endsWith("/")){
            connectionString += getDatabase();
        } else {
            connectionString += "/" + getDatabase();
        }

        return connectionString;
    }
}
