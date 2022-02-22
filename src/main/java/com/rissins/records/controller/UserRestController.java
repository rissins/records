package com.rissins.records.controller;

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
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

//    @GetMapping
//    public List<User> findByUser() {
//        return userService.findAllByUser();
//    }


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
        System.out.println("userResponse.getUserId = " + userResponse.getUserId());
        System.out.println("userResponse.getUserPassword = " + userResponse.getUserPassword());
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(userResponse.getUserPassword())
                .build();
        int login = userService.login(user);
        if (login == 1) {
            log.info("{} 로그인 실패", userResponse.getUserId());
        } else {
            session.setAttribute("sessionId", userResponse.getUserId());
            log.info("{} 로그인 성공", userResponse.getUserId());
        }
    }

    @GetMapping("/user/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
