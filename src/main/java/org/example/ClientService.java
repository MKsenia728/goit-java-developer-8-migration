package org.example;

import org.example.entity.Client;
import org.example.exeption.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final PreparedStatement createSt;
    private final PreparedStatement selectMaxId;
    private final PreparedStatement selectById;
    private final PreparedStatement setById;
    private final PreparedStatement deleteById;
    private final Statement selectAll;

    public ClientService(Connection connection) {
        try {
            createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?)");
            selectMaxId = connection.prepareStatement("SELECT max(id) AS max_id FROM client");
            selectById = connection.prepareStatement("SELECT name FROM client WHERE id = ?");
            setById = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
            deleteById = connection.prepareStatement("DELETE FROM client WHERE id = ?");
            selectAll = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    Додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
    public long create(String name) {
        long id = -1;
        try {
            ClientValidation.nameValidation(name);
            createSt.setString(1, name);
            createSt.executeUpdate();
            try (ResultSet rs = selectMaxId.executeQuery()) {
                if (rs.next()) {
                    id = rs.getLong("max_id");
                } else {
                    throw new RuntimeException("Impossible to get last id");
                }
            }
        } catch (ValidationException e) {
            System.err.println("Validation failed: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Create error", e);
        }
        return id;
    }

    //    повертає назву клієнта з ідентифікатором id
    public String getById(long id) {
        try {
            ClientValidation.idValidation(id);
            selectById.setLong(1, id);
            try (ResultSet rs = selectById.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (ValidationException e) {
            System.err.println("Validation failed: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //    встановлює нове ім'я name для клієнта з ідентифікатором id
    public void setName(long id, String name) {

        try {
            ClientValidation.idValidation(id);
            ClientValidation.nameValidation(name);
            setById.setString(1, name);
            setById.setLong(2, id);
            setById.executeUpdate();
        } catch (ValidationException e) {
            System.err.println("Validation failed: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    видаляє клієнта з ідентифікатором id
    public void deleteById(long id) {
        try {
            ClientValidation.idValidation(id);
            deleteById.setLong(1, id);
            deleteById.executeUpdate();
        } catch (ValidationException e) {
            System.err.println("Validation failed: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client (цей клас створи сам, у ньому має бути 2 поля - id та name)
    public List<Client> listAll() {
        List<Client> list = new ArrayList<>();
        try (ResultSet rs = selectAll.executeQuery("SELECT id, name FROM client")) {
            while (rs.next()) {
                list.add(new Client(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String[] args) {
        Database db = Database.getInstance();

        ClientService cs = new ClientService(db.getConnection());
        System.out.println("Clients before changes: " + cs.listAll());

        System.out.println("Created client has id: " + cs.create("New client"));

        System.out.println("A client with id = 1 has name : " + cs.getById(1));

        cs.setName(2, "Client with changed name");

        cs.deleteById(6);

        System.out.println("Clients after changes: " + cs.listAll());

    }
}
