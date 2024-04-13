package com.hillel.spring.entity;


import com.hillel.spring.model.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description = null;

    @Column(name = "status", length = 63)
    private TaskStatus taskStatus = TaskStatus.NEW;

    @Column(name = "date_deadline_at")
    private Date dateDeadlineAt = new Date();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "id_member", referencedColumnName = "id")
    private User user = null;
}