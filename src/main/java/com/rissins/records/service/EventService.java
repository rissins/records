package com.rissins.records.service;

import com.rissins.records.domain.Event;
import com.rissins.records.repository.EventRepository;
import com.rissins.records.utils.requestlimit.Throttle;
import com.rissins.records.utils.requestlimit.Throttle2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final Throttle throttle;
    private final Throttle2 throttle2;

    public void save(Event event) {

        eventRepository.save(event);
    }

    public int findSizeByUserId(String userId) {
        return eventRepository.findAllByUserId(userId).size() + 1;
    }

    @Transactional(readOnly = true)
    public Optional<Event> findById(Long id) {
//        return eventRepository.findById(id);
        if (throttle2.setting(id)) {
            return eventRepository.findById(id);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Event> findAllByUserId(String userId) {
        log.info("{} 유저의 총 갯수는 {} 개 입니다.", userId, (long) eventRepository.findAllByUserId(userId).size());
        if (throttle.setting(userId)) {
            return eventRepository.findAllByUserId(userId);
        } else {
            return null;
        }
//        return eventRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
