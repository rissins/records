package com.rissins.records.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventResponse {

    private String id;
    private String title;
    private String context;
    private String textColor;
    private String backgroundColor;
    private String borderColor;
    private String loginUser;
    private String userId;
}
