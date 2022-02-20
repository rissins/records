package com.rissins.records.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rissins.records.domain.Event;
import com.rissins.records.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
