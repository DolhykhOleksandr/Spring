package com.hillel.spring;

import com.hillel.spring.model.Priority;
import com.hillel.spring.model.Task;
import com.hillel.spring.model.TaskStatus;
import com.hillel.spring.model.User;
import com.hillel.spring.service.task.TaskService;
import com.hillel.spring.service.user.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

import static java.lang.System.*;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        TaskService taskService = context.getBean(TaskService.class);
        UserService userService = context.getBean(UserService.class);

        Task task = new Task();
        task.setId(1L);
        task.setName("Task");
        task.setStatus(TaskStatus.NEW);
        task.setDescription("Test");
        task.setDeadline(LocalDate.now().plusDays(7));
        task.setPriority(String.valueOf(Priority.MEDIUM));

        Task task1 = new Task();
        task1.setId(2L);
        task1.setName("Task1");
        task1.setStatus(TaskStatus.NEW);
        task1.setDescription("Test");
        task1.setDeadline(LocalDate.now().plusDays(6));
        task1.setPriority(String.valueOf(Priority.LOW));

        Task task2 = new Task();
        task2.setId(3L);
        task2.setName("Task2");
        task2.setStatus(TaskStatus.COMPLETED);
        task2.setDescription("Test");
        task2.setDeadline(LocalDate.now().plusDays(1));
        task2.setPriority(String.valueOf(Priority.HIGH));

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("User1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("User2");

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("User3");

        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        taskService.createTask(task);
        taskService.createTask(task1);
        taskService.createTask(task2);

        userService.assignTask(user1.getId(), task.getId());
        userService.assignTask(user2.getId(), task1.getId());
        userService.assignTask(user3.getId(), task2.getId());

        out.println("Tasks assigned to User1: " + userService.getTasksByUserId(user1.getId()));
        out.println("Tasks assigned to User2: " + userService.getTasksByUserId(user2.getId()));
        out.println("Tasks assigned to User3: " + userService.getTasksByUserId(user3.getId()));

        userService.assignTask(user1.getId(), task1.getId());

        taskService.changeStatusOfTask(task.getId(), TaskStatus.IN_PROGRESS);

        out.println("Tasks assigned to User1 after reassignment: " + userService.getTasksByUserId(user1.getId()));

        context.close();
    }
}
