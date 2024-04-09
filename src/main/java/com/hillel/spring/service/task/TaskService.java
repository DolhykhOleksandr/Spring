package com.hillel.spring.service.task;

import com.hillel.spring.dao.TaskDAO;

import com.hillel.spring.model.TaskStatus;
import com.hillel.spring.model.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@Getter
@RequiredArgsConstructor
public class TaskService {

    private final TaskDAO taskDAO;

    public int createNewTask(Task task) {
        try {
            return taskDAO.saveTask(task);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create new task");
        }
    }

    public void deleteTaskById(Integer id) {
        try {
            taskDAO.deleteTask(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete task with ID: " + id);
        }
    }

    public TaskStatus changeStatusOfTask(Integer taskId, TaskStatus taskStatus) {
        try {
            Task task = taskDAO.getTaskById(taskId);
            task.setTaskStatus(taskStatus);
            taskDAO.updateTaskStatus(task);
            return task.getTaskStatus();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to change status of task with ID: " + taskId);
        }
    }

    public Task updateTask(Task task) {
        try {
            return taskDAO.updateTask(task);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to update task");
        }
    }

    public Task getTaskById(Integer idTask) {
        try {
            return taskDAO.getTaskById(idTask);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to retrieve task with ID: " + idTask);
        }
    }

    public List<Task> getTasks() {
        try {
            return taskDAO.getAllTasks();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to retrieve tasks");
        }
    }
}