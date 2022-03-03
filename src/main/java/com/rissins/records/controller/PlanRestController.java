package com.rissins.records.controller;

import com.rissins.records.dto.PlanResponse;
import com.rissins.records.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{ids}")
    public void delete(@PathVariable List<Long> ids) {
        planService.deleteByIds(ids);
    }
}
