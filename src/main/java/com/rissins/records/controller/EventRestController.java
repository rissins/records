package com.rissins.records.controller;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

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
                .userId(eventResponse.getUserId())
                .build();
        eventService.save(event);
    }

    @GetMapping("/{loginId}")
    public List<Event> search(@PathVariable String loginId) {
        log.info("Bbs Search Success");
//        Event event = eventService.findById(loginId).get();
//        System.out.println("event.toString() = " + event.toString());
//        return eventService.findById(loginId).get();
        return eventService.findAllByUserId(loginId);
    }
}
