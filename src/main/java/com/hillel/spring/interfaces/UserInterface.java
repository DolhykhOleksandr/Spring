package com.hillel.spring.interfaces;

import com.hillel.spring.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserInterface {
    Optional<User> findById(UUID id);

    Iterable<User> findAll();

    User save(User user);

    void delete(User entity);

    boolean isExistByName(String name);

    boolean isExistByName(String name, UUID id);
}