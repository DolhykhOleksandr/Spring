package com.hillel.spring.service.task;



import com.hillel.spring.model.Task;
import com.hillel.spring.model.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task createTask(Task task);
    Optional updateTask(Long id, Task task);
    void deleteTask(Long id);
    TaskStatus changeStatusOfTask(Long id, String newStatus);
    List<Task> getOrderedTask(String orderBy);

    boolean taskExists(Long id);
}
