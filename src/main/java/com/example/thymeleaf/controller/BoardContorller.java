package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.repository.BoardRepository;
import com.example.thymeleaf.service.BoardService;
import com.example.thymeleaf.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/board") //경로지정
public class BoardContorller {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model ,@PageableDefault(size = 2) Pageable pageable ,
                       @RequestParam(required = false,defaultValue = "") String searchText) {
     //   Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int startPage = 1;//Math.max(boards.getPageable().getPageNumber() - 4, 1);
        int endPage = boards.getTotalPages();//Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);
        model.addAttribute("boards",boards);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "board/list";
    }
    @GetMapping("/form")
    public String form(Model model , @RequestParam(required = false) Long id) {
        if(id==null) {
            model.addAttribute("board",new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication) {
        boardValidator.validate(board,bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
//        Authentication a = SecurityContextHolder.getContext().getAuthentication(); 2번째 방법
        String username = authentication.getName();
        boardService.save(username,board);
//        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
