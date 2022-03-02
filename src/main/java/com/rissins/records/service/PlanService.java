package com.rissins.records.service;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import com.rissins.records.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public void save(PlanResponse planResponse) {
        Plan plan = Plan.builder()
                .title(planResponse.getTitle())
                .context(planResponse.getContext())
                .userId(planResponse.getUserId())
                .build();

        planRepository.save(plan);
    }

    public List<Plan> findAllByUserId(String userId) {
        return planRepository.findAllByUserId(userId);
    }
}
