package com.rissins.records.dto;

import com.rissins.records.domain.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanResponse {

    private Long id;
    private String title;
    private String context;
    private String userId;

    public Plan toEntity() {
        return Plan.builder()
                .title(title)
                .context(context)
                .userId(userId)
                .build();
    }
}
