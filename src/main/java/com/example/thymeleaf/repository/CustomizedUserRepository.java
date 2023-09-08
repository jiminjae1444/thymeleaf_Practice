package com.example.thymeleaf.repository;

import com.example.thymeleaf.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByCustomUsername(String username);
}
