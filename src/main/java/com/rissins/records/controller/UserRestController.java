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
        userService.save(userResponse);
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, UserResponse userResponse) {

        HttpSession session = request.getSession();
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(userResponse.getUserPassword())
                .build();
        Status login = userService.login(user);
        if (login == Status.ACCEPTED) {
            session.setAttribute("sessionId", userResponse.getUserId());
            log.info("{} : {}}", userResponse.getUserId(), login);
        } else {
            log.info("{} : {}", userResponse.getUserId(), login);
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    @PostMapping("/check")
    public String overlapCheck(String userId) {
        String returnValue = "";
        Status status = userService.overlapCheckByUserId(userId);
        if (status == Status.ACCEPTED) {
            returnValue = "사용가능한 아이디입니다.";
        } else {
            returnValue = "중복된 아이디입니다.";
        }
        return returnValue;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
