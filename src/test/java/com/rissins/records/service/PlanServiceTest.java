package com.rissins.records.service;

import com.rissins.records.PlanFixtures;
import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class PlanServiceTest {

    @Autowired
    PlanService planService;

    private PlanFixtures planFixtures;

    @BeforeEach
    void setUp() {
        this.planFixtures = new PlanFixtures();
    }

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    void 계획_삭제() {
        //given
        String userId = "testId";
        Long id = planService.findAllByUserId(userId).get(0).getId();
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        //when
        planService.deleteByIds(ids);
        //then
        List<Plan> planFindByUserId = planService.findAllByUserId(userId);
        Assertions.assertThat(planFindByUserId).isEmpty();
    }


}