package com.rissins.records.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private static int dbCount = 0;
    private final EventRepository eventRepository;
    private final S3Service s3Service;


    @Transactional
    public void save(Event event) {
        eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public int findSizeByUserId(String userId) {
        return eventRepository.findAllByUserId(userId).size() + 1;
    }

    @Transactional(readOnly = true)
    public EventResponse findById(Long id) {
        return eventRepository.findById(id).get().toResponse();
    }

    @Transactional(readOnly = true)
//    @Cacheable(key = "#userId", value = "userId")
    public List<EventResponse> findAllByUserId(String userId) {
        List<Event> allByUserId = eventRepository.findAllByUserId(userId);
        dbCount++;
        log.info("{} 유저의 총 갯수는 {} 개 입니다.", userId, (long) eventRepository.findAllByUserId(userId).size());
        List<EventResponse> eventResponses = new ArrayList<>();
            allByUserId.forEach(event -> {
                EventResponse eventResponse = EventResponse.builder()
                        .id(event.getId())
                        .userId(event.getUserId())
                        .start(event.getStart())
                        .end(event.getEnd())
                        .title(event.getTitle())
                        .context(event.getContext())
                        .backgroundColor(event.getBackgroundColor())
                        .textColor(event.getTextColor())
                        .allDay(event.getAllDay())
                        .file(event.getFile())
                        .build();
                eventResponses.add(eventResponse);
            });
        return eventResponses;
    }

    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public int getDbCount() {
        return dbCount;
    }

    public String findFileById(Long id) {
        return findById(id).getFile();
    }

    @Transactional
    public void eventSave(Map<String, Object> param, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileUpload = s3Service.upload(file);
        param.put("file", fileUpload);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventResponse eventResponse = mapper.convertValue(param, EventResponse.class);
        Event event = eventResponse.toEntity();
        eventRepository.save(event);
    }

    @Transactional
    public void eventUpdate(Map<String, Object> param, MultipartFile file, Long id) throws IOException, NoSuchAlgorithmException {
        String fileUpload = s3Service.upload(file);
        param.put("file", fileUpload);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventResponse eventResponse = mapper.convertValue(param, EventResponse.class);
        Event event = eventRepository.findById(id).get();
        event.updateInfo(eventResponse);
    }
}
