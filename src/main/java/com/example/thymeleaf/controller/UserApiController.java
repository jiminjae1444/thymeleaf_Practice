package com.example.thymeleaf.controller;

import com.example.thymeleaf.mapper.UserMapper;
import com.example.thymeleaf.model.Board;
import com.example.thymeleaf.model.QUser;
import com.example.thymeleaf.model.User;
import com.example.thymeleaf.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.example.thymeleaf.model.QUser.user;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserApiController {
        @Autowired
        private UserRepository repository;
        @Autowired
        private UserMapper userMapper;

        @GetMapping("/users")
        Iterable<User> all(@RequestParam(required = false) String method,@RequestParam(required = false) String text) {
//            List<User> users = repository.findAll();
            Iterable<User> users = null;
            if("query".equals((method))){
                users = repository.findByUsernameQuery(text);
            } else if ("nativeQuery".equals(method)) {
                users = repository.findByUsernameNativeQuery(text);
            } else if ("querydsl".equals(method)) {
               QUser user = QUser.user;
//                BooleanExpression b = user.username.contains(text);
//                if(true){
//                 b = b.and(user.username.eq("HI"));
//                }
               Predicate predicate = user.username.contains(text);

               users = repository.findAll(predicate);

            } else if ("querydslCustom".equals(method)) {
                users = repository.findByCustomUsername(text);
            } else if ("mybatis".equals(method)) {
               users = userMapper.getUsers(text);
            } else {
                users = repository.findAll();
            }
//            log.debug("getBoards().size() 호출전");
//            log.debug("getBoards().size() : {}", users.get(0).getBoards().size());
//            log.debug("getBoards().size() 호출후");
            return users;
        }

        @PostMapping("/users")
        User newUser(@RequestBody User newUser) {
            return repository.save(newUser);
        }

        // Single item

        @GetMapping("/users/{id}")
        User one(@PathVariable Long id) {

            return repository.findById(id).orElse(null);
        }

        @PutMapping("/users/{id}")
        User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

            return repository.findById(id)
                    .map(user -> {
//                        user.setTitle(newUser.getTitle());
//                        user.setContent(newUser.getContent());
//                        user.setWriter(newUser.getWriter());
                        user.setBoards(newUser.getBoards());
                        for(Board board : user.getBoards()) {
                            board.setUser(user);
                        }
                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        newUser.setId(id);
                        return repository.save(newUser);
                    });
        }

        @DeleteMapping("/users/{id}")
        void deleteUser(@PathVariable Long id) {
            repository.deleteById(id);
        }
}
