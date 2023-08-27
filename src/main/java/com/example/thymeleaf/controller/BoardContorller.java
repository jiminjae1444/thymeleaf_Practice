package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/board") //경로지정
public class BoardContorller {
    @Autowired
    private BoardRepository boardRepository;
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "board/list";
    }
}
