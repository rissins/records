package com.rissins.records.service;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlanServiceTest {

    @Autowired
    PlanService planService;

    @Test
    void 계획_추가() {
        //given
        String title = "계획제목테스트";
        String content = "계획내용테스트";
        String userId = "testId";
        PlanResponse planResponse = PlanResponse.builder()
                .userId(userId)
                .title(title)
                .context(content)
                .build();
        //when
        planService.save(planResponse);
        List<Plan> allByUserId = planService.findAllByUserId(userId);
        //then
        Assertions.assertThat(allByUserId.get(0).getTitle()).isEqualTo(title);
    }

}