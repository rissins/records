package com.rissins.records;

import com.rissins.records.domain.User;
import com.rissins.records.dto.UserResponse;

import static com.rissins.records.common.CommonFixtures.*;

public class UserFixtures {
    public UserResponse getUserResponse() {
        return UserResponse.builder()
                .userId(USER_ID)
                .userPassword(USER_PASSWORD)
                .build();
    }

    public UserResponse getUserResponse(String password) {
        return UserResponse.builder()
                .userId(USER_ID)
                .userPassword(password)
                .build();
    }

    public UserResponse getUserResponseWithInCorrectId() {
        return UserResponse.builder()
                .userId(INCORRECT_USER_ID)
                .userPassword(USER_PASSWORD)
                .build();
    }

    public UserResponse getUserResponseWithInCorrectPassword() {
        return UserResponse.builder()
                .userId(USER_ID)
                .userPassword(INCORRECT_USER_PASSWORD)
                .build();
    }


    public User failUser() {
        return User.builder()
                .userId(USER_ID)
                .userPassword(FAIL_USER_PASSWORD)
                .build();
    }
}
