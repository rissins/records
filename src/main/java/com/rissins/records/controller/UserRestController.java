package com.rissins.records.controller;

import com.rissins.records.domain.constant.Status;
import com.rissins.records.domain.User;
import com.rissins.records.dto.UserResponse;
import com.rissins.records.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    @PostMapping("/user/signup")
    public void signUp(UserResponse userResponse) {
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(userResponse.getUserPassword())
                .build();
        userService.save(user);
    }

    @PostMapping("/user/login")
    public void login(HttpServletRequest request, UserResponse userResponse) {
        HttpSession session = request.getSession();
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(userResponse.getUserPassword())
                .build();
        Status login = userService.login(user);
        if (login == Status.ACCEPTED) {
            session.setAttribute("sessionId", userResponse.getUserId());
            log.info("{} : 로그인 성공", userResponse.getUserId());
        } else {
            log.info("{} : 로그인 실패", userResponse.getUserId());
        }
    }

    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
