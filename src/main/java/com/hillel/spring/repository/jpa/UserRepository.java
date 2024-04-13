package com.hillel.spring.repository.jpa;

import com.hillel.spring.entity.User;
import com.hillel.spring.interfaces.UserInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "app.use_source", havingValue = "jpa")
public interface UserRepository extends JpaRepository<User, UUID>, UserInterface {
    @Override
    @Query("SELECT COUNT(m.id) > 0 FROM User m WHERE LOWER(m.name) = :name")
    boolean isExistByName(@Param("name") String name);

    @Override
    @Query("SELECT COUNT(m.id) > 0 FROM User m WHERE LOWER(m.name) = :name AND m.id != :existUserId")
    boolean isExistByName(@Param("name") String name, @Param("existUserId") UUID id);
}