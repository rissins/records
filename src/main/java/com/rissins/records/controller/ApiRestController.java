package com.rissins.records.controller;

import com.rissins.records.domain.Event;
import com.rissins.records.domain.Plan;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import com.rissins.records.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ApiRestController {

    private final EventService eventService;
    private final PlanService planService;
    private static int dbRead = 0;

    @GetMapping("/event")
    public List<EventResponse> findByUserId(@RequestParam String userId) {
        log.info("DB 호출");
        dbRead++;
        return eventService.findAllByUserId(userId);
    }

    @GetMapping("/event/{eventId}")
    public EventResponse findById(@PathVariable Long eventId) {
        return eventService.findById(eventId);
    }

    @GetMapping("/plan")
    public List<Plan> findAllByUserId(@RequestParam String userId) {
        return planService.findAllByUserId(userId);
    }

    @GetMapping("/plan/{id}")
    public Optional<Plan> findPlanById(@PathVariable Long id) {
        return planService.findById(id);
    }

    @GetMapping("/count")
    public String dbCount() {
        int dbCount = eventService.getDbCount();
        return "사용자 호출 횟수 : " + dbRead + "   실제 호출 횟수 : " + dbCount;
    }
}
