package com.hillel.spring.service.user;
import com.hillel.spring.dao.UserDAO;
import com.hillel.spring.model.Task;
import com.hillel.spring.model.User;
import com.hillel.spring.service.task.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter
@RequiredArgsConstructor
public class UserService {
    private final TaskService taskService;
    private final UserDAO userDAO;

    public User createUser(User user)  {
        try {
            if (userDAO.saveUser(user) == 1) {
                return user;
            } else {
                throw new IllegalArgumentException("Failed to create user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int userId) {
        try {
            userDAO.deleteUser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasksByUserId(int userId)  {
        try {
            return userDAO.getTasksByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserForCurrentTask(Integer taskId)  {
        try {
            return userDAO.getUserForCurrentTask(taskId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User setTaskForUser(Task task, User user) {
        task.setUserId(user.getId());
        user.addTasks(List.of(task));
        taskService.updateTask(task);
        return user;
    }

    public List<User> getAllUsers()  {
        Map<Integer, User> usersMap = null;
        try {
            usersMap = userDAO.getUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersMap.values().stream()
                .map(user -> {
                    List<Task> tasks = getTasksByUserId(user.getId());
                    user.addTasks(tasks);
                    return user;
                })
                .collect(Collectors.toList());
    }

    public User getUserById(Integer id)  {
        try {
            List<Task> tasks = getTasksByUserId(id);
            User user = userDAO.getUserById(id);
            user.addTasks(tasks);
            return user;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to retrieve user with ID: " + id);
        }
    }
}