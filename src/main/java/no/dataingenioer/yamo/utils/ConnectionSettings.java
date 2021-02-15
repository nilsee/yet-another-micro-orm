package no.dataingenioer.yamo.utils;

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

    // TODO: Get from configuration file
    public static ConnectionSettings getConnectionSettings(String connectionSettingsName){
        return new ConnectionSettings(connectionSettingsName,
                "jdbc:postgresql://localhost:5432",
                "hibernatedb",
                "org.postgresql.Driver",
                "hibernate",
                "forjava");
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() { return driver; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() { return database; }

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
