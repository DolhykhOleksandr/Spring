package com.hillel.spring.service.task;


import com.hillel.spring.model.Task;
import com.hillel.spring.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    TaskStatus changeStatusOfTask(Long id, TaskStatus newStatus);
    List<Task> getOrderedTask(String orderBy);
}
