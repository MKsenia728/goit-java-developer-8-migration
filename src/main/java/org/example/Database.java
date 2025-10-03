package org.example;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database();
    @Getter
    private Connection connection;
    private final String DB_CONNECTION_URL = "jdbc:h2:./megasoft";

    private Database() {
        try {
            connection = DriverManager.getConnection(DB_CONNECTION_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {

        return INSTANCE;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
