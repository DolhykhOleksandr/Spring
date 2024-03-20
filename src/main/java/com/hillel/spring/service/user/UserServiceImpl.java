package com.hillel.spring.service.user;

import com.hillel.spring.model.Task;
import com.hillel.spring.model.User;

import com.hillel.spring.service.task.TaskService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    private final TaskService taskService;

    public UserServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public User createUser(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }


    @Override
    public User updateUser(Long id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return updatedUser;
            }
        }
        throw new IllegalArgumentException("User not found with id: " + id);
    }

    @Override
    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void assignTask(Long userId, Long taskId) {
        Optional<User> optionalUser = getUserById(userId);
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        if (optionalUser.isPresent() && optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setAssignedUserId(userId);
            taskService.updateTask(taskId, task);
        }
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        List<Task> list = new ArrayList<>();
        for (Task task : taskService.getAllTasks()) {
            if (task.getAssignedUserId() != null && task.getAssignedUserId().equals(userId)) {
                list.add(task);
            }
        }
        return list;
    }
    public boolean userExists(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserForTask(Long taskId) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        if (optionalTask.isPresent()) {
            Long userId = optionalTask.get().getAssignedUserId();
            return getUserById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found for task with id: " + taskId));
        }
        throw new IllegalArgumentException("Task not found with id: " + taskId);
    }
}