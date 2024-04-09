package com.hillel.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class Task {
    private final Integer id;
    private Integer userId;
    private String name;
    private TaskStatus taskStatus;
    private String description;
    private LocalDate deadline;
    private Integer priority;
}