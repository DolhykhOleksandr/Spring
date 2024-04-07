package com.hillel.spring.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class User {
    private final Integer id;
    private List<Integer> tasksId = new ArrayList<>();
    private String fullName;

    public void addTasks(List<Task> tasks) {
        for (Task task : tasks) {
            tasksId.add(task.getId());
        }
    }
}