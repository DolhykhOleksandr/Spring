package com.hillel.spring.controller;

import com.hillel.spring.model.Task;
import com.hillel.spring.model.User;
import com.hillel.spring.service.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController

@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user)  {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping("/getTasks/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Integer userId)  {
        return ResponseEntity.ok(userService.getTasksByUserId(userId));
    }

    @GetMapping("/getUserByTaskId/{taskId}")
    public ResponseEntity<User> getUserForCurrentTask(@PathVariable Integer taskId)  {
        return ResponseEntity.ok(userService.getUserForCurrentTask(taskId));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id)  {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getUsers()  {
        return ResponseEntity.of(Optional.ofNullable(userService.getAllUsers()));
    }

    @PostMapping("/setTask")
    public ResponseEntity<User> setTask(@RequestBody Task task, @RequestBody User user) {
        return ResponseEntity.ok(userService.setTaskForUser(task, user));
    }
}