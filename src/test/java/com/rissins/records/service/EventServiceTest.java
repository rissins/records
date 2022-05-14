package com.rissins.records.service;

import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    S3Service s3Service;

    @Order(1)
    @Test
    void 인증_추가() throws IOException, NoSuchAlgorithmException {
        //given
        String userId = "testId";
        String title = "testTitle";
        String context = "testContext";
        String textColor = "#02343f";
        String backgroundColor = "#02343f";
        Boolean allDay = true;
        byte[] data = new byte[]{1, 2, 3, 4};
        InputStream stream = new ByteArrayInputStream(data);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", stream);

        Event event = Event.builder()
                .externalId(userId + eventService.findSizeByUserId(userId))
                .title(title)
                .context(context)
                .textColor(textColor)
                .backgroundColor(backgroundColor)
                .userId(userId)
                .allDay(allDay)
                .file(s3Service.upload(file))
                .build();
        //when
        eventService.save(event);
        EventResponse findByUserId = eventService.findAllByUserId(userId).get(0);

        //then
        Assertions.assertThat(findByUserId.getTitle()).isEqualTo(title);
        Assertions.assertThat(findByUserId.getContext()).isEqualTo(context);
    }

    @Order(2)
    @Test
    void 인증_삭제() {
        //given
        String userId = "testId";
        Long id = eventService.findAllByUserId(userId).get(0).getId();
        String fileById = eventService.findFileById(id);
        //when
        eventService.delete(id);
        s3Service.delete(fileById);

        Optional<Event> byId = eventService.findById(id);
        //then
        Assertions.assertThat(byId).isEmpty();
    }
}