package com.rissins.records.service;

import com.rissins.records.domain.Event;
import com.rissins.records.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public void save(Event event) {
        eventRepository.save(event);
    }

    public String findUid() {
        return String.valueOf(eventRepository.count() + 1);
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public List<Event> findAllByUserId(String userId) {
        return eventRepository.findAllByUserId(userId);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}
