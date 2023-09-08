package com.example.thymeleaf.repository;

import com.example.thymeleaf.model.QUser;
import com.example.thymeleaf.model.User;
import com.fasterxml.jackson.databind.BeanProperty;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    @PersistenceContext
    private EntityManager em;
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findByCustomUsername(String username) {
        QUser quser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<User> users = query.select(quser)
                .from(quser)
                .where(quser.username.contains(username))
                .fetch();
        return users;
    }
}