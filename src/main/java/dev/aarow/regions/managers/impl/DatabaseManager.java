package dev.aarow.regions.managers.impl;

import dev.aarow.regions.data.database.DatabaseAuthentication;
import dev.aarow.regions.managers.Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager extends Manager {

    private Connection connection;
    private Statement statement;

    @Override
    public void setup() {
        DatabaseAuthentication authentication = new DatabaseAuthentication();

        try{
            synchronized (this) {
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection("jdbc:mysql://" + authentication.getHost() + ":" + authentication.getPort() + "/" + authentication.getDatabase(), authentication.getUsername(), authentication.getPassword());

                statement = connection.createStatement();
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }

        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS regionsplugin (" +
                    "NAME TEXT NOT NULL, " +
                    "FIRSTCORNER TEXT NOT NULL, " +
                    "SECONDCORNER TEXT NOT NULL, " +
                    "WHITELISTED TEXT NOT NULL, " +
                    "FLAGS TEXT NOT NULL, " +
                    "CREATED BIGINT NOT NULL, " +
                    "PRIMARY KEY (NAME(255))" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
