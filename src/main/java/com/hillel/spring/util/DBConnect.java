package com.hillel.spring.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DBConnect {
    @SneakyThrows
    public Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "user";
        String password = "secret";
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}