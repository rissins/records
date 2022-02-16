package com.rissins.records.controller;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/event")
public class EventRestController {

    private final EventService eventService;

    @PostMapping
    public void save(EventResponse eventResponse) {

        Event event = Event.builder()
                .id(eventResponse.getLoginUser() + eventService.findUid())
                .title(eventResponse.getTitle())
                .context(eventResponse.getContext())
                .textColor(eventResponse.getTextColor())
                .backgroundColor(eventResponse.getBackgroundColor())
                .build();
        eventService.save(event);
    }

    @GetMapping("/{searchId}")
    public Event search(@PathVariable Long searchId) {
        log.info("Bbs Search Success");
        Event event = eventService.findById(searchId).get();
        System.out.println("event.toString() = " + event.toString());
        return eventService.findById(searchId).get();
    }
}
