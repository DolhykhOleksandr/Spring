package com.hillel.spring.controller;

import com.hillel.spring.model.Task;
import com.hillel.spring.service.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("No tasks found");
        } else {
            return ResponseEntity.ok(tasks);
        }
    }

    @ApiOperation("Get task by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
        return ResponseEntity.ok(task);
    }


    @ApiOperation("Create a new task")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
       Task createTask = taskService.createTask(task);
        return ResponseEntity.ok(createTask);
    }

    @ApiOperation("Update an existing task")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateUser(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> updatedTaskOpt = taskService.updateTask(id, task);
        if (updatedTaskOpt.isPresent()) {
            Task updatedTask = updatedTaskOpt.get();
            return ResponseEntity.ok(updatedTask);
        } else {
            throw new IllegalArgumentException("Task not updated with id: " + id);
        }
    }

    @ApiOperation("Delete a task")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (taskService.taskExists(id)) {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted successfully");
        } else {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
    }
}
