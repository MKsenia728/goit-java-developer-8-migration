package org.example;

import org.example.model.*;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Connection connection;

    public DatabaseQueryService(Connection connection) {
        this.connection = connection;
    }

    //    find_longest_project.sql
    public List<ProjectDuration> findLongestProject() {
        List<ProjectDuration> list = new ArrayList<>();
        String sql = new FileReader(Path.of("sql", "find_longest_project.sql")).readSQLFromFile();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ProjectDuration(
                        rs.getString("name"),
                        rs.getInt("month_count")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обработке результата", e);
        }
        return list;
    }

    //    find_max_projects_client.sql
    public List<ClientCountProject> findMaxProjectsClient() {
        List<ClientCountProject> list = new ArrayList<>();
        String sql = new FileReader(Path.of("sql", "find_max_projects_client.sql")).readSQLFromFile();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ClientCountProject(
                        rs.getString("name"),
                        rs.getInt("project_count")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findMaxProjectsClient", e);
        }
        return list;
    }

    //    find_max_salary_worker.sql
    public List<WorkerSalary> findMaxSalaryWorker() {
        List<WorkerSalary> list = new ArrayList<>();
        String sql = new FileReader(Path.of("sql", "find_max_salary_worker.sql")).readSQLFromFile();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new WorkerSalary(
                        rs.getString("name"),
                        rs.getInt("salary")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findMaxSalaryWorker", e);
        }
        return list;
    }

    //    find_youngest_eldest_workers.sql
    public List<WorkerAge> findYoungestEldestWorkers() {
        List<WorkerAge> list = new ArrayList<>();
        String sql = new FileReader(Path.of("sql", "find_youngest_eldest_workers.sql")).readSQLFromFile();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new WorkerAge(
                        TypeAge.valueOf(rs.getString("type")),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("birthday"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса findYoungestEldestWorkers", e);
        }
        return list;
    }

    //    print_project_prices.sql
    public List<ProjectPrice> printProjectPrices() {
        List<ProjectPrice> list = new ArrayList<>();
        String sql = new FileReader(Path.of("sql", "print_project_prices.sql")).readSQLFromFile();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ProjectPrice(
                        rs.getString("name"),
                        rs.getInt("price")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении запроса printProjectPrices", e);
        }
        return list;
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();
        DatabaseQueryService dbQS = new DatabaseQueryService(db.getConnection());

        List<ProjectDuration> list = dbQS.findLongestProject();
        list.forEach(System.out::println);

        List<ClientCountProject> ccpList = dbQS.findMaxProjectsClient();
        ccpList.forEach(System.out::println);

        List<WorkerSalary> wsList = dbQS.findMaxSalaryWorker();
        wsList.forEach(System.out::println);

        List<WorkerAge> workerAgeList = dbQS.findYoungestEldestWorkers();
        workerAgeList.forEach(System.out::println);

        List<ProjectPrice> projectPriceList = dbQS.printProjectPrices();
        projectPriceList.forEach(System.out::println);
    }
}
