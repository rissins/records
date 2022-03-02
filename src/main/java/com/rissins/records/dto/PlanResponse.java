package com.rissins.records.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlanResponse {

    private String title;
    private String context;
    private String userId;
}
