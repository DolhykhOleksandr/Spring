package com.hillel.spring.controller;

import com.hillel.spring.model.Task;
import com.hillel.spring.service.task.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



import java.util.List;

@Api(tags = "Task Management")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation("Get all tasks")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @ApiOperation("Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<String> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskService.getTaskById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
            return ResponseEntity.ok(task.toString());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    @ApiOperation("Create a new task")
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @ApiOperation("Update an existing task")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            taskService.updateTask(id, task);
            return ResponseEntity.ok("Task updated successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

        @ApiOperation("Delete a task")
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteTask (@PathVariable Long id){
            try {
                taskService.deleteTask(id);
                return ResponseEntity.ok("Task deleted successfully");
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            }
        }
    }
