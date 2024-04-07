package com.hillel.spring.controller;

import com.hillel.spring.model.TaskStatus;
import com.hillel.spring.model.Task;
import com.hillel.spring.service.task.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createNewTask(task));
    }

    @GetMapping("/getAll")
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
    }

    @PutMapping("/changeStatus/{taskId}/{taskStatus}")
    public ResponseEntity<TaskStatus> changeStatus(@PathVariable Integer taskId, @PathVariable TaskStatus taskStatus) {
        return ResponseEntity.ok(taskService.changeStatusOfTask(taskId, taskStatus));
    }

    @GetMapping("/get")
    public ResponseEntity<Task> getTaskById(@RequestBody Integer id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
}