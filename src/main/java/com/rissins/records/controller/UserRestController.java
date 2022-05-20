package com.rissins.records.controller;

import com.rissins.records.domain.constant.Status;
import com.rissins.records.domain.User;
import com.rissins.records.dto.UserResponse;
import com.rissins.records.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;


    @PostMapping("/signup")
    public void signUp(UserResponse userResponse) {
        userService.signUp(userResponse);
    }

    @PostMapping("/login")
    public Status login(HttpServletRequest request, UserResponse userResponse) {

        HttpSession session = request.getSession();
        Status loginStatus = userService.login(userResponse);
        if (loginStatus == Status.ACCEPTED) {
            session.setAttribute("sessionId", userResponse.getUserId());
            log.info("{} : 로그인 {}", userResponse.getUserId(), loginStatus);
        } else {
            log.info("{} : 로그인 {}", userResponse.getUserId(), loginStatus);
        }
        return loginStatus;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    @PostMapping("/check")
    public String overlapCheck(String userId) {
        Status status = userService.overlapCheckByUserId(userId);
        if (status == Status.ACCEPTED) {
            return "사용가능한 아이디입니다.";
        } else {
            return "중복된 아이디입니다.";
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);

    }

    @GetMapping
    public User findByUserId(@RequestParam String userId) {
        return userService.findByUserId(userId);
    }

}
