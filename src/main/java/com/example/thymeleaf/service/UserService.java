package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.model.Role;
import com.example.thymeleaf.model.User;
import com.example.thymeleaf.repository.BoardRepository;
import com.example.thymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.net.PasswordAuthentication;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public User save(User user) throws Exception {
        String encoderPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);

        //트랜잭션 처리
//        if(true) {
//            throw new RuntimeException("예외 발생!!!");
//        }
//        if(true) {
//            throw new Exception("예외 발생!!!");
//        }
        //사용자가 가입인사글 자동작성
        Board board = new Board();
        board.setTitle("안녕하세요!");
        board.setContent("반갑습니다.");
        board.setUser(savedUser);
        boardRepository.save(board);

        return savedUser;


    }
    public boolean usernameCollect(String username) {
        return userRepository.existsByUsername(username);
    }
}
