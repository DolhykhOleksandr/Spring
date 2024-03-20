package com.hillel.spring.service.task;

import com.hillel.spring.model.Task;
import com.hillel.spring.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class TaskServiceImpl implements TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public List<Task> getAllTasks() {
        return tasks;
    }
    @Override
    public Optional<Task> getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public Task createTask(Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }
@Override
    public Task updateTask(Long id, Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getId().equals(id)) {
                updatedTask.setId(id);
                tasks.set(i, updatedTask);
                return updatedTask;
            }
        }
    throw new IllegalArgumentException("Task not found with id: " + id);
    }

    public void deleteTask(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public TaskStatus changeStatusOfTask(Long id, TaskStatus newStatus) {
        Optional<Task> optionalTask = getTaskById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(newStatus);
            updateTask(id, task);
            return newStatus;
        }
        throw new IllegalArgumentException("Task not found with id: " + id);
    }
    public boolean taskExists(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Task> getOrderedTask(String orderBy) {
        return switch (orderBy) {
            case "priority" -> {
                List<Task> list = new ArrayList<>(tasks);
                list.sort(Comparator.comparing(Task::getPriority));
                yield list;
            }
            case "status" -> {
                List<Task> list = new ArrayList<>(tasks);
                list.sort(Comparator.comparing(Task::getStatus));
                yield list;
            }
            case "deadline" -> {
                List<Task> list = new ArrayList<>(tasks);
                list.sort(Comparator.comparing(Task::getDeadline));
                yield list;
            }
            default -> new ArrayList<>();
        };
    }
}
