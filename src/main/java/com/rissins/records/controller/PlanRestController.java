package com.rissins.records.controller;

import com.rissins.records.dto.PlanResponse;
import com.rissins.records.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/plan")
public class PlanRestController {

    private final PlanService planService;

    @PostMapping
    public void save(PlanResponse planResponse) {
        planService.save(planResponse);
    }
}
