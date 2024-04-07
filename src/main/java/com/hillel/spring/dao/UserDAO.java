package com.hillel.spring.dao;

import com.hillel.spring.model.Task;
import com.hillel.spring.model.User;
import com.hillel.spring.util.DBConnect;

import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.*;

@Component
public class UserDAO {
    private final DBConnect dbConnect;

    public UserDAO(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public int saveUser(User user) throws SQLException {
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, full_name) VALUES (?, ?)")) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFullName());
            return statement.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    public List<Task> getTasksByUserId(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = Utils.extractTaskFromResultSet(resultSet);
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public User getUserForCurrentTask(int taskId) throws SQLException {
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {
            statement.setInt(1, taskId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUserById(resultSet.getInt("user_id"));
                }
                return null;
            }
        }
    }

    public User updateUser(User user) throws SQLException {
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET full_name = ? WHERE id = ?")) {
            statement.setString(1, user.getFullName());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
            return user;
        }
    }

    public Map<Integer, User> getUsers() throws SQLException {
        Map<Integer, User> users = new HashMap<>();
        try (Connection connection = dbConnect.connect();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
                while (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    users.put(user.getId(), user);
                }
            }
        }
        return users;
    }

    public User getUserById(int userId) throws SQLException {
        try (Connection connection = dbConnect.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
        }
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String fullName = resultSet.getString("full_name");
        User user = new User(id);
        user.setFullName(fullName);
        return user;
    }
}
