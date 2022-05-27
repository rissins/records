package com.rissins.records;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;

import static com.rissins.records.common.CommonFixtures.*;

public class PlanFixtures {
    public Plan getPlan() {
        return Plan.builder()
                .id(ID)
                .userId(USER_ID)
                .title(TITLE)
                .context(CONTENT)
                .build();
    }

    public Plan getUpdatePlan() {
        return Plan.builder()
                .userId(USER_ID)
                .title(UPDATE_CONTENT)
                .context(UPDATE_CONTENT)
                .build();
    }

    public PlanResponse getPlanResponse() {
        return PlanResponse.builder()
                .userId(USER_ID)
                .title(TITLE)
                .context(CONTENT)
                .build();
    }

}
