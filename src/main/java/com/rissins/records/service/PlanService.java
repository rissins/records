package com.rissins.records.service;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import com.rissins.records.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public void save(PlanResponse planResponse) {
        Plan plan = Plan.builder()
                .title(planResponse.getTitle())
                .context(planResponse.getContext())
                .build();

        planRepository.save(plan);
    }
}
