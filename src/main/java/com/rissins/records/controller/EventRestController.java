package com.rissins.records.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import com.rissins.records.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/event")
public class EventRestController {

    private final EventService eventService;
    private final S3Service s3Service;

    @PostMapping
    public void save(@RequestPart(value = "key") Map<String, Object> param, MultipartFile file) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventResponse eventResponse = mapper.convertValue(param, EventResponse.class);

        Event event = Event.builder()
                .externalId(eventResponse.getLoginUser() + eventService.findUid())
                .title(eventResponse.getTitle())
                .context(eventResponse.getContext())
                .textColor(eventResponse.getTextColor())
                .backgroundColor(eventResponse.getBackgroundColor())
                .userId(eventResponse.getUserId())
                .allDay(eventResponse.getAllDay())
                .file(s3Service.upload(file))
                .build();
        eventService.save(event);
    }

    @GetMapping("/all")
    public List<Event> search(@RequestParam String userId) {
//        log.info("Bbs Search Success");
//        Event event = eventService.findById(loginId).get();
//        System.out.println("event.toString() = " + event.toString());
//        return eventService.findById(loginId).get();
        return eventService.findAllByUserId(userId);
    }

//    @GetMapping("/all")
//    public List<Event> findAll() {
//        return eventService.findAll();
//    }
}
