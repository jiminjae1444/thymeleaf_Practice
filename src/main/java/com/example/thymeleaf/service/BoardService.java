package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.model.User;
import com.example.thymeleaf.repository.BoardRepository;
import com.example.thymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board) {
        User user = userRepository.findByUsername(username);
        board.setCreate_date(LocalDateTime.now());
        board.setUser(user);
        return boardRepository.save(board);
    }
}
