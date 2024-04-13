package com.hillel.spring.repository.dao;

import com.hillel.spring.entity.Task;
import com.hillel.spring.interfaces.TaskInterface;
import com.hillel.spring.model.TaskStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "app.use_source", havingValue = "dao")
public class TaskDao implements TaskInterface {
    private final DataSource dataSource;

    public TaskDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<Task> findById(UUID id) {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("SELECT * FROM task WHERE id = ?")) {
            statement.setObject(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(this.build(result));
            }
            return Optional.empty();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public Iterable<Task> findAll() {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("SELECT * FROM task")) {
            ResultSet result = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (result.next()) {
                tasks.add(this.build(result));
            }
            return tasks;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public Task save(Task task) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "INSERT INTO task (id_member, name, description, status, priority, date_deadline_at) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setObject(1, null != task.getUser() ? task.getUser().getId() : null);
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setObject(4, null != task.getTaskStatus() ? task.getTaskStatus().name() : null);
            statement.setDate(5, this.getSqlData(task.getDateDeadlineAt()));
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                task.setId(result.getObject("id", UUID.class));
            }
            return task;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void delete(Task task) {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("DELETE FROM task WHERE id = ?")) {
            statement.setObject(1, task.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public boolean isExistByName(String name) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM task WHERE LOWER(name) = ?"
                )
        ) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getBoolean("count");
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public boolean isExistByName(String name, UUID existTaskId) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM task WHERE LOWER(name) = ? AND id != ?"
                )
        ) {
            statement.setString(1, name);
            statement.setObject(2, existTaskId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getBoolean("count");
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean existsByNameAndStatus(String name, TaskStatus status) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM task WHERE LOWER(name) = ? AND status = ?"
                )
        ) {
            statement.setString(1, name);
            statement.setString(2, status.name()); // предполагается, что TaskStatus представляет собой перечисление с методом name()
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getBoolean("count");
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
   @Override
    public boolean existsByStatus(TaskStatus status) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM task WHERE status = ?"
                )
        ) {
            statement.setString(1, status.name()); // предполагается, что TaskStatus представляет собой перечисление с методом name()
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getBoolean("count");
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Task build(ResultSet result) throws SQLException {
        return Task.builder()
                .id(result.getObject("id", UUID.class))
                .name(result.getString("name"))
                .description(result.getString("description"))
                .dateDeadlineAt(this.getSimpleData(result.getDate("date_deadline_at")))
                .build()
                ;
    }
    private java.util.Date getSimpleData(java.sql.Date date) {
        if (null != date) {
            return new java.util.Date(date.getTime());
        }
        return null;
    }
    private java.sql.Date getSqlData(java.util.Date date) {
        if (null != date) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }
}