package com.hillel.spring.interfaces;

import com.hillel.spring.entity.Task;
import com.hillel.spring.model.TaskStatus;
import java.util.Optional;
import java.util.UUID;

public interface TaskInterface {
    Optional<Task> findById(UUID id);

    Iterable<Task> findAll();

    Task save(Task task);

    void delete(Task entity);

    boolean isExistByName(String name);

    boolean isExistByName(String name, UUID id);

    boolean existsByNameAndStatus(String name, TaskStatus status);

    boolean existsByStatus(TaskStatus status);
}