package com.rissins.records.service;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
import com.rissins.records.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteByIds(List<Long> ids) {
//        planRepository.deleteById(id);
        for (Long id : ids) {
            planRepository.deleteById(id);
        }
    }

    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }
}
