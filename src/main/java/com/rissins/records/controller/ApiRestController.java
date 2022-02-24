package com.rissins.records.controller;

import com.rissins.records.domain.Event;
import com.rissins.records.service.EventService;
import com.rissins.records.service.UserService;
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
    private final UserService userService;

    @GetMapping("/event")
    public List<Event> findByUserId(@RequestParam String userId) {
        return eventService.findAllByUserId(userId);
    }

    @GetMapping("/event/{eventId}")
    public Optional<Event> findById(@PathVariable Long eventId) {
        return eventService.findById(eventId);
    }
}
