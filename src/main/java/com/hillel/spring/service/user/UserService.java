package com.hillel.spring.service.user;


import com.hillel.spring.model.Task;
import com.hillel.spring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    Optional<User> updateUser(Long id, User user);
    void deleteUser(Long id);
    void assignTask(Long userId, Long taskId);
    List<Task> getTasksByUserId(Long userId);
    User getUserForTask(Long taskId);

    boolean userExists(Long id);
}

