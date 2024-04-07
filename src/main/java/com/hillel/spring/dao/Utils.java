package com.hillel.spring.dao;

import com.hillel.spring.model.TaskStatus;
import com.hillel.spring.model.Task;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@UtilityClass
public class Utils {


    Task extractTaskFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        String name = resultSet.getString("name");
        TaskStatus taskStatus = TaskStatus.valueOf(resultSet.getString("status"));
        String description = resultSet.getString("description");
        LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
        int priority = resultSet.getInt("priority");
        return new Task(id, userId, name, taskStatus, description, deadline, priority);
    }
}