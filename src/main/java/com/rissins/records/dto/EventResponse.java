package com.rissins.records.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private String id;
    private String title;
    private String context;
    private String textColor;
    private String backgroundColor;
    private String loginUser;
    private String userId;
    private Boolean allDay;
}
