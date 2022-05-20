package com.rissins.records.dto;

import com.rissins.records.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String userPassword;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPassword(userPassword)
                .build();
    }

    public User toEntityWithEncoderPassword(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .userPassword(encodedPassword)
                .build();
    }
}
