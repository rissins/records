package com.rissins.records.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.service.EventService;
import com.rissins.records.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
        eventService.eventSave(param, file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        String fileById = eventService.findFileById(id);
        s3Service.delete(fileById);
        eventService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestPart(value = "key") Map<String, Object> param, MultipartFile file, @PathVariable Long id) throws IOException, NoSuchAlgorithmException {
        eventService.eventUpdate(param, file, id);
    }
}
