package com.rissins.records.service;

import com.rissins.records.PlanFixtures;
import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Transactional
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
    @Rollback(value = false)
    void 계획_추가() {
        //given
        PlanResponse testPlanResponse = planFixtures.getPlanResponse();
        //when
        planService.save(testPlanResponse);
        List<Plan> allByUserId = planService.findAllByUserId(testPlanResponse.getUserId());
        //then
        Assertions.assertThat(allByUserId.get(0).getTitle()).isEqualTo(testPlanResponse.getTitle());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    void 계획_삭제() {
        //given
        List<Long> ids = new ArrayList<>();
        String testUserId = planFixtures.getPlanResponse().getUserId();
        Long id = planService.findAllByUserId(testUserId).get(0).getId();
        ids.add(id);
        //when
        planService.deleteByIds(ids);
        List<Plan> planFindByUserId = planService.findAllByUserId(testUserId);
        //then
        Assertions.assertThat(planFindByUserId).isEmpty();
    }
}