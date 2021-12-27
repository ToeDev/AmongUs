package org.toedev.amongus.sql;

import org.toedev.amongus.AmongUs;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Database {

    private final AmongUs amongUs;
    private Connection connection;
    private Statement statement;

    public Database(AmongUs amongUs) {
        this.amongUs = amongUs;
    }

    public void connect() {
        connection = null;
        try {
            File dbFile = new File(amongUs.getDataFolder(), "core.db");
            if(!dbFile.exists()) {
                dbFile.createNewFile();
            }
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
