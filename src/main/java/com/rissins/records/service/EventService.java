package com.rissins.records.service;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public void save(Event event) {
        eventRepository.save(event);
    }
}
