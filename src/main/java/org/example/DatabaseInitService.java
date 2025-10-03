package org.example;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {
    private final Connection connection;
    private final String initDBQuery;

    public DatabaseInitService(Connection connection, String initDBQuery) {
        this.connection = connection;
        this.initDBQuery = initDBQuery;
    }

    public void initDB() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(initDBQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        String sqlForInitDB = new FileReader(Path.of("sql", "init_db.sql")).readSQLFromFile();
        DatabaseInitService dbIS = new DatabaseInitService(db.getConnection(), sqlForInitDB);
        dbIS.initDB();
    }
}
