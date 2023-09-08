package com.example.thymeleaf.repository;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor <User> ,CustomizedUserRepository{
    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();
    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    @Query(value = "select * from User u where u.username like %?1%",nativeQuery = true) //원하는 sql문 사용 가능
    List<User> findByUsernameNativeQuery(String username);
}
