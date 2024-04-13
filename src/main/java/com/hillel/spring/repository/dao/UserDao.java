package com.hillel.spring.repository.dao;

import com.hillel.spring.entity.User;
import com.hillel.spring.interfaces.UserInterface;
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
public class UserDao implements UserInterface {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<User> findById(UUID id) {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("SELECT * FROM member WHERE id = ?")) {
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
    public Iterable<User> findAll() {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("SELECT * FROM member")) {
            ResultSet result = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(this.build(result));
            }
            return users;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public User save(User user) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "INSERT INTO member (name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getName());
            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if (result.next()) {
                user.setId(result.getObject("id", UUID.class));
            }
            return user;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void delete(User user) {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement("DELETE FROM member WHERE id = ?")) {
            statement.setObject(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public boolean isExistByName(String name) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM member WHERE LOWER(name) = ?"
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
    public boolean isExistByName(String name, UUID existMemberId) {
        try (
                PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                        "SELECT COUNT(id) > 0 AS count FROM member WHERE LOWER(name) = ? AND id != ?"
                )
        ) {
            statement.setString(1, name);
            statement.setObject(2, existMemberId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getBoolean("count");
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    private User build(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getObject("id", UUID.class))
                .name(result.getString("name"))
                .tasks(new ArrayList<>())
                .build()
                ;
    }
}