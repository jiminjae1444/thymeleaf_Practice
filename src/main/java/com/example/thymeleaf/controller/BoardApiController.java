package com.example.thymeleaf.controller;

import java.util.List;

import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
public class BoardApiController {
        @Autowired
        private BoardRepository repository;

        @GetMapping("/boards")
        List<Board> all(@RequestParam (required = false,defaultValue = "") String title,
                        @RequestParam(required = false,defaultValue = "") String content) {
            if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
                return repository.findAll();
            } else {
//                return repository.findByTitle(title);
                return repository.findByTitleOrContent(title,content);
            }
        }

        @PostMapping("/boards")
        Board newBoard(@RequestBody Board newBoard) {
            return repository.save(newBoard);
        }

        // Single item

        @GetMapping("/boards/{id}")
        Board one(@PathVariable Long id) {

            return repository.findById(id).orElse(null);
        }

        @PutMapping("/boards/{id}")
        Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

            return repository.findById(id)
                    .map(board -> {
                        board.setTitle(newBoard.getTitle());
                        board.setContent(newBoard.getContent());
                        board.setWriter(newBoard.getWriter());
                        return repository.save(board);
                    })
                    .orElseGet(() -> {
                        newBoard.setId(id);
                        return repository.save(newBoard);
                    });
        }

        @DeleteMapping("/boards/{id}")
        void deleteBoard(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
