package com.hillel.spring.controller;

import com.hillel.spring.model.User;
import com.hillel.spring.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "User Management")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Get all users")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            throw new IllegalArgumentException("No users found");
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @ApiOperation("Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return ResponseEntity.ok("User found by id: " + user.toString());
    }

    @ApiOperation("Create a new user")
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User createUser = userService.createUser(user);
        return ResponseEntity.ok("User created successfully: " + createUser.toString());
    }

    @ApiOperation("Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
           User updateUser = userService.updateUser(id, user);
            return ResponseEntity.ok("Task updated successfully: " + updateUser.toString());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Task not updated with id: " + id);
        }
    }

    @ApiOperation("Delete a user")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userService.userExists(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }
}
