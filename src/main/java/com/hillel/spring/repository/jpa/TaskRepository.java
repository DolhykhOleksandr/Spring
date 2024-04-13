package com.hillel.spring.repository.jpa;

import com.hillel.spring.entity.Task;
import com.hillel.spring.interfaces.TaskInterface;
import com.hillel.spring.model.TaskStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "app.use_source", havingValue = "jpa")
public interface TaskRepository extends JpaRepository<Task, UUID>, TaskInterface {
    @Override
    @Query("SELECT COUNT(t.id) > 0 FROM Task t WHERE LOWER(t.name) = :name")
    boolean isExistByName(@Param("name") String name);
    @Override
    @Query("SELECT COUNT(t.id) > 0 FROM Task t WHERE LOWER(t.name) = :name AND t.id != :existTaskId")
    boolean isExistByName(@Param("name") String name, @Param("existTaskId") UUID id);
    @Override
    @Query("SELECT COUNT(t.id) > 0 FROM Task t WHERE LOWER(t.name) = :name AND t.taskStatus = :status")
    boolean existsByNameAndStatus(@Param("name") String name, @Param("status") TaskStatus status);
    @Override
    @Query("SELECT COUNT(t.id) > 0 FROM Task t WHERE t.taskStatus = :status")
    boolean existsByStatus(@Param("status") TaskStatus status);
}