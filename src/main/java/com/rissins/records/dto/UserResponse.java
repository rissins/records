package com.rissins.records.dto;

import com.rissins.records.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {
    private String userId;
    private String userPassword;
}
