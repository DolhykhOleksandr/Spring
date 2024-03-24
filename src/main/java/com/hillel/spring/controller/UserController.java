package com.hillel.spring.controller;

import com.hillel.spring.model.User;
import com.hillel.spring.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return ResponseEntity.ok(user);
    }


    @ApiOperation("Create a new user")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @ApiOperation("Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> updatedUserOpt = userService.updateUser(id, user);
        if (updatedUserOpt.isPresent()) {
            User updatedUser = updatedUserOpt.get();
            return ResponseEntity.ok(updatedUser);
        } else {
            throw new IllegalArgumentException("User not updated with id: " + id);
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
