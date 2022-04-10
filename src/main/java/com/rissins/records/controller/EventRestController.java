package com.rissins.records.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import com.rissins.records.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/event")
public class EventRestController {

    private final EventService eventService;
    private final S3Service s3Service;

    @PostMapping
    public void save(@RequestPart(value = "key") Map<String, Object> param, MultipartFile file) throws IOException, NoSuchAlgorithmException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventResponse eventResponse = mapper.convertValue(param, EventResponse.class);

        String userId = eventResponse.getUserId();

        Event event = Event.builder()
                .externalId(userId + eventService.findSizeByUserId(userId))
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


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestPart(value = "key") Map<String, Object> param, MultipartFile file, @PathVariable Long id) {
        Optional<Event> event = eventService.findById(id);
        event.ifPresent(selectEvent -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            EventResponse eventResponse = mapper.convertValue(param, EventResponse.class);

            String userId = eventResponse.getUserId();
            try {
                Event newEvent = Event.builder()
                        .id(id)
                        .externalId(userId + eventService.findSizeByUserId(userId))
                        .title(eventResponse.getTitle())
                        .context(eventResponse.getContext())
                        .textColor(eventResponse.getTextColor())
                        .backgroundColor(eventResponse.getBackgroundColor())
                        .userId(eventResponse.getUserId())
                        .allDay(eventResponse.getAllDay())
                        .file(s3Service.upload(file))
                        .build();
                eventService.save(newEvent);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }

    @GetMapping("/count")
    public int dbCount() {
        return eventService.getDbCount();
    }
}
