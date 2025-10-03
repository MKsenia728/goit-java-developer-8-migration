package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabasePopulateService {
    private final Connection connection;
    private static final Path DATA_PATH = Path.of("sql", "data");

    public DatabasePopulateService(Connection connection) {
        this.connection = connection;
    }

    private void populateWorkers() {
        String sql = "INSERT INTO worker (name, birthday, level, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            List<String> datasList = Files.readAllLines(DATA_PATH.resolve("populateWorkerData.sql"));

            for (String data : datasList) {
                String[] dataFields = data.split(",");
                pst.setString(1, dataFields[0].trim());
                pst.setString(2, dataFields[1].trim());
                pst.setString(3, dataFields[2].trim());
                pst.setInt(4, Integer.parseInt(dataFields[3].trim()));
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateClients() {
        String sql = "INSERT INTO client (name) VALUES (?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            List<String> datasList = Files.readAllLines(DATA_PATH.resolve("populateClientData.sql"));

            for (String data : datasList) {
                pst.setString(1, data.trim());
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateProjects() {
        String sql = "INSERT INTO project (client_id, name, start_date, finish_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            List<String> datasList = Files.readAllLines(DATA_PATH.resolve("populateProjectData.sql"));

            for (String data : datasList) {
                String[] dataFields = data.split(",");
                pst.setInt(1, Integer.parseInt(dataFields[0].trim()));
                pst.setString(2, dataFields[1].trim());
                pst.setString(3, dataFields[2].trim());
                pst.setString(4, dataFields[3].trim());
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateProjectsWorker() {
        String sql = "INSERT INTO project_worker (project_id, worker_id) VALUES (?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            List<String> datasList = Files.readAllLines(DATA_PATH.resolve("populateProjectWorkerData.sql"));

            for (String data : datasList) {
                String[] dataFields = data.split(",");
                pst.setInt(1, Integer.parseInt(dataFields[0].trim()));
                pst.setInt(2, Integer.parseInt(dataFields[1].trim()));
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateDB() throws SQLException {
        connection.setAutoCommit(false);
        try {
            populateWorkers();
            populateClients();
            populateProjects();
            populateProjectsWorker();
            System.out.println("Populate Success!!!!!");
            connection.commit();
        } catch (Exception _) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        DatabasePopulateService dbPS = new DatabasePopulateService(db.getConnection());
        try {
            dbPS.populateDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
