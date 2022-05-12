package com.rissins.records.service;

import com.rissins.records.domain.User;
import com.rissins.records.domain.constant.Status;
import com.rissins.records.dto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    final String joinTestId = "test111";
    final String joinTestPassword = "test111";

    final String testId = "test222";
    final String testPassword = "test222";

    @Order(1)
    @Test
    void 테스트_계정생성() {
        //given
        UserResponse userResponse = new UserResponse(joinTestId, joinTestPassword);
        //when
        Status signUpResult = userService.signUp(userResponse);
        //then
        Assertions.assertThat(signUpResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(2)
    @Test
    void 중복된_아이디() {
        //given

        //when
        Status overlapCheckResult = userService.overlapCheckByUserId(joinTestId);
        //then
        Assertions.assertThat(overlapCheckResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(3)
    @Test
    void 사용가능한_아이디() {
        //given
        //when
        Status overlapCheckResult = userService.overlapCheckByUserId(testId);
        //then
        Assertions.assertThat(overlapCheckResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(4)
    @Test
    void 로그인_성공() {
        //given
        User user = User.builder()
                .userId(joinTestId)
                .userPassword(joinTestPassword)
                .build();
        //when
        Status loginResult = userService.login(user);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(5)
    @Test
    void 로그인_실패_비밀번호_불일치() {
        //given
        User user = User.builder()
                .userId(joinTestId)
                .userPassword(testPassword)
                .build();
        //when
        Status loginResult = userService.login(user);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(6)
    @Test
    void 로그인_실패_아이디_불일치() {
        //given
        User user = User.builder()
                .userId(testId)
                .userPassword(joinTestPassword)
                .build();
        //when
        Status loginResult = userService.login(user);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(7)
    @Test
    void 회원탈퇴_성공() {
        //given

        //when
        User byUserId = userService.findByUserId(joinTestId);
        Status deleteResult = userService.deleteById(byUserId.getId());
        //then
        Assertions.assertThat(deleteResult).isEqualByComparingTo(Status.ACCEPTED);
    }
}