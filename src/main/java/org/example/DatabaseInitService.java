package org.example;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    private final String DB_CONNECTION_URL = "jdbc:h2:./megasoft";

    public void initDB() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(DB_CONNECTION_URL, null, null)
                .load();
        flyway.migrate();
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();

       new DatabaseInitService().initDB();
    }
}
