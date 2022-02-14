package com.rissins.records.controller;

import com.rissins.records.domain.User;
import com.rissins.records.dto.UserResponse;
import com.rissins.records.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    private List<User> findByUser() {
        return userService.findAllByUser();
    }

    @PostMapping("/user/signup")
    public void signUp(UserResponse userResponse) {
        User user = User.builder()
                .userId(userResponse.getUserId())
                .userPassword(userResponse.getUserPassword())
                .build();
        userService.save(user);
    }
}
