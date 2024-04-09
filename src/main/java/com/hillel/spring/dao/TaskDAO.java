package com.hillel.spring.dao;

import com.hillel.spring.model.Task;
import com.hillel.spring.util.DBConnect;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDAO {

    private final DBConnect dbConnect;

    public TaskDAO(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    public int saveTask(Task task) throws SQLException {
        if (task.getUserId() == null) {
            try (PreparedStatement statement = dbConnect.connect().prepareStatement(
                    "INSERT INTO tasks (id, name, status, description, deadline, priority) VALUES (?,?,?,?,?,?);"
            )) {
                return saveTaskWithoutUserId(task, statement);
            }
        } else {
            try (PreparedStatement statement = dbConnect.connect().prepareStatement(
                    "INSERT INTO tasks (id, user_id, name, status, description, deadline, priority) VALUES (?,?,?,?,?,?,?);"
            )) {
                return saveTaskWithUserId(task, statement);
            }
        }
    }

    private int saveTaskWithUserId(Task task, PreparedStatement statement) throws SQLException {
        statement.setInt(1, task.getId());
        statement.setInt(2, task.getUserId());
        statement.setString(3, task.getName());
        statement.setString(4, task.getTaskStatus().name());
        statement.setString(5, task.getDescription());
        statement.setDate(6, Date.valueOf(task.getDeadline()));
        statement.setInt(7, task.getPriority());
        return statement.executeUpdate();
    }

    private int saveTaskWithoutUserId(Task task, PreparedStatement statement) throws SQLException {
        statement.setInt(1, task.getId());
        statement.setString(2, task.getName());
        statement.setString(3, task.getTaskStatus().name());
        statement.setString(4, task.getDescription());
        statement.setDate(5, Date.valueOf(task.getDeadline()));
        statement.setInt(6, task.getPriority());
        return statement.executeUpdate();
    }

    public void deleteTask(int taskId) throws SQLException {
        try (PreparedStatement statement = dbConnect.connect().prepareStatement(
                "DELETE FROM tasks WHERE id = ?"
        )) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        }
    }

    public int updateTaskStatus(Task task) throws SQLException {
        try (PreparedStatement statement = dbConnect.connect().prepareStatement(
                "UPDATE tasks SET status = ? WHERE id = ?"
        )) {
            statement.setString(1, task.getTaskStatus().name());
            statement.setInt(2, task.getId());
            return statement.executeUpdate();
        }
    }

    public Task getTaskById(int id) throws SQLException {
        try (PreparedStatement statement = dbConnect.connect().prepareStatement(
                "SELECT * FROM tasks WHERE id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Utils.extractTaskFromResultSet(resultSet);
                }
                throw new IllegalArgumentException();
            }
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        try (Statement statement = dbConnect.connect().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");
            while (resultSet.next()) {
                Task task = Utils.extractTaskFromResultSet(resultSet);
                tasks.add(task);
            }
        }
        return tasks;
    }


    public Task updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET user_id = ?, name = ?, status = ?, description = ?, deadline = ?, priority = ? WHERE id = ?";
        try (PreparedStatement statement = dbConnect.connect().prepareStatement(sql)) {
            statement.setInt(1, task.getUserId());
            statement.setString(2, task.getName());
            statement.setString(3, task.getTaskStatus().name());
            statement.setString(4, task.getDescription());
            statement.setDate(5, Date.valueOf(task.getDeadline()));
            statement.setInt(6, task.getPriority());
            statement.setInt(7, task.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return task;
            }
        }
        return null;
    }
}