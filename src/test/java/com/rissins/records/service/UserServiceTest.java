package com.rissins.records.service;

import com.rissins.records.UserFixtures;
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

    private UserFixtures userFixtures;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userFixtures = new UserFixtures();
    }

    @Order(1)
    @Test
    void 테스트_계정생성() {
        //given
        UserResponse userResponse = userFixtures.getUserResponse();
        //when
        Status signUpResult = userService.signUp(userResponse);
        //then
        Assertions.assertThat(signUpResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(2)
    @Test
    void 중복된_아이디() {
        //given
        String userId = userFixtures.getUserResponse().getUserId();
        //when
        Status overlapCheckResult = userService.overlapCheckByUserId(userId);
        //then
        Assertions.assertThat(overlapCheckResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(3)
    @Test
    void 사용가능한_아이디() {
        //given
        String userId = userFixtures.getUserResponse().getUserId() + "1";
        //when
        Status overlapCheckResult = userService.overlapCheckByUserId(userId);
        //then
        Assertions.assertThat(overlapCheckResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(4)
    @Test
    void 로그인_성공() {
        //given
        UserResponse userResponse = userFixtures.getUserResponse();
        //when
        Status loginResult = userService.login(userResponse);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.ACCEPTED);
    }

    @Order(5)
    @Test
    void 로그인_실패_비밀번호_불일치() {
        //given
        UserResponse userResponseWithInCorrectPassword = userFixtures.getUserResponseWithInCorrectPassword();
        //when
        Status loginResult = userService.login(userResponseWithInCorrectPassword);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(6)
    @Test
    void 로그인_실패_아이디_불일치() {
        //given
        UserResponse userResponseWithInCorrectId = userFixtures.getUserResponseWithInCorrectId();
        //when
        Status loginResult = userService.login(userResponseWithInCorrectId);
        //then
        Assertions.assertThat(loginResult).isEqualByComparingTo(Status.DENIED);
    }

    @Order(7)
    @Test
    void 회원탈퇴_성공() {
        //given
        String userId = userFixtures.getUserResponse().getUserId();
        //when
        User byUserId = userService.findByUserId(userId);
        Status deleteResult = userService.deleteById(byUserId.getId());
        //then
        Assertions.assertThat(deleteResult).isEqualByComparingTo(Status.ACCEPTED);
    }
}