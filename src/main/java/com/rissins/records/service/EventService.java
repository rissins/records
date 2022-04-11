package com.rissins.records.service;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//
@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private static int dbCount = 0;
    private final EventRepository eventRepository;


    public void save(Event event) {
        eventRepository.save(event);
    }

    public int findSizeByUserId(String userId) {
        return eventRepository.findAllByUserId(userId).size() + 1;
    }

    @Transactional(readOnly = true)
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId", value = "userId")
    public List<EventResponse> findAllByUserId(String userId) {
        List<Event> allByUserId = eventRepository.findAllByUserId(userId);
        dbCount++;
        log.info("{} 유저의 총 갯수는 {} 개 입니다.", userId, (long) eventRepository.findAllByUserId(userId).size());
        log.info(allByUserId.toString());
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
}
