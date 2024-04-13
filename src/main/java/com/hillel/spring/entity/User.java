package com.hillel.spring.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Task.class, mappedBy = "user")
    List<Task> tasks = new ArrayList<>();
}