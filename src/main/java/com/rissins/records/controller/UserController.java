package com.rissins.records.controller;

import com.rissins.records.domain.User;
import com.rissins.records.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user/signup")
    public String signUp() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
}
