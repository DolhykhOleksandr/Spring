package com.hillel.spring.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private String priority;
    private TaskStatus status;
    private Long assignedUserId;
}
