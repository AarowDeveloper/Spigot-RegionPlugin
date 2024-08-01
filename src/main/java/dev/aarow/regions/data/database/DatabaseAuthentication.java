package dev.aarow.regions.data.database;

import dev.aarow.regions.plugin.RegionsPlugin;

public class DatabaseAuthentication {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public DatabaseAuthentication(){
        this.host = RegionsPlugin.getInstance().getConfig().getString("MYSQL.HOST");
        this.port = RegionsPlugin.getInstance().getConfig().getInt("MYSQL.PORT");
        this.database = RegionsPlugin.getInstance().getConfig().getString("MYSQL.DATABASE");
        this.username = RegionsPlugin.getInstance().getConfig().getString("MYSQL.AUTHENTICATION.USERNAME");
        this.password = RegionsPlugin.getInstance().getConfig().getString("MYSQL.AUTHENTICATION.PASSWORD");
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
