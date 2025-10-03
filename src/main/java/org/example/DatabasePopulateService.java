package org.example;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePopulateService {
    private final Connection connection;
    private final String populateDBQuery;


    public DatabasePopulateService(Connection connection, String populateDBQuery) {
        this.connection = connection;
        this.populateDBQuery = populateDBQuery;
    }

    public void populateDB() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(populateDBQuery);
            System.out.println("Database populated successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        String sqlForPopulateDB = new FileReader(Path.of("sql", "populate_db.sql")).readSQLFromFile();
        DatabasePopulateService dbPS = new DatabasePopulateService(db.getConnection(), sqlForPopulateDB);
        dbPS.populateDB();
    }
}
