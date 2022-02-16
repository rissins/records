package com.rissins.records.controller;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                .id(eventResponse.getId())
                .title(eventResponse.getTitle())
                .context(eventResponse.getContext())
                .textColor(eventResponse.getTextColor())
                .backgroundColor(eventResponse.getBackgroundColor())
                .build();

        eventService.save(event);
    }
}
