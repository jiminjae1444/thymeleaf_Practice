package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.User;
import com.example.thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login() {

        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password , @RequestParam String password2, Model model) throws Exception {
        if (userService.usernameCollect(username)) {
            model.addAttribute("usernameError","이미 사용중인 username입니다!");
            return "account/register";
        }
        if (!password.equals(password2)) {
            model.addAttribute("passwordError","password가 일치하지 않습니다.");
            return  "account/register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return "redirect:/";
    }
}
