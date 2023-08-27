package com.example.thymeleaf.repository;

import com.example.thymeleaf.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
