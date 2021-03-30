package no.dataingenioer.yamo.core.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents configuration data to connect to a database using JDBC connection
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public class ConnectionSettings {

    /**
     * JDBC URL to database server
     */
    private String server;

    /**
     * Name of database on server
     */
    private String database;

    /**
     * JDBC Driver to use for spesific database
     */
    private String driver;

    /**
     * Database username
     */
    private String username;

    /**
     * Database password
     */
    private String password;

    /**
     * No instances without data is nessessary
     */
    protected ConnectionSettings() {
    }

    /**
     *
     * @param server
     * @param database
     * @param driver
     * @param username
     * @param password
     */
    public ConnectionSettings(String server, String database, String driver, String username, String password) {

        this.server = server;
        this.database = database;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    /**
     * Factory method for connection settings
     *
     * @param connectionSettingsFileName Name of properties file with configuration data
     * @return ConnectionSettings object from given properties file
     * @throws IOException
     */
    public static ConnectionSettings getConnectionSettings(String connectionSettingsFileName)
            throws IOException {

        Properties properties =  readPorperties(connectionSettingsFileName);

        return new ConnectionSettings(
                properties.getProperty("yamo.server"),
                properties.getProperty("yamo.database"),
                properties.getProperty("yamo.driver"),
                properties.getProperty("yamo.username"),
                properties.getProperty("yamo.password")
        );
    }

    /**
     * Reads connection settings for a properties file
     * @param fileName
     * @return
     * @throws IOException
     */
    private static Properties readPorperties(String fileName)
            throws IOException {

        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(fileName);

        properties.load(fileInputStream);

        return properties;
    }

    /**
     *
     * @return
     */
    public String getServer() {
        return server;
    }

    /**
     *
     * @return
     */
    public String getDatabase() {
        return database;
    }

    /**
     *
     * @return
     */
    public String getDriver() {
        return driver;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return jdbc:<database type>://<server>:<port>/<databsae name>
     */
    public String getUrl() {

        String connectionString = getServer();

        if(connectionString.endsWith("/")) {

            connectionString += getDatabase();
        } else {

            connectionString += "/" + getDatabase();
        }

        return connectionString;
    }
}
