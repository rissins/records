package com.rissins.records.service;

import com.rissins.records.domain.Plan;
import com.rissins.records.dto.PlanResponse;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    S3Service s3Service;
    @Autowired
    PlanService planService;

    @Order(1)
    @Test
    void 인증_추가() throws IOException, NoSuchAlgorithmException {
        //given
        Map<String, Object> param = new HashMap<>();


        String userId = "testId";
        String title = "testTitle";
        String context = "testContext";
        String textColor = "#02343f";
        String backgroundColor = "#02343f";
        Boolean allDay = true;
        byte[] data = new byte[]{1, 2, 3, 4};
        InputStream stream = new ByteArrayInputStream(data);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", stream);

        PlanResponse planResponse = PlanResponse.builder()
                .title("계획테스트제목")
                .context("계획테스트내용")
                .userId(userId)
                .build();

        Plan plan1 = planService.findById(23L).get();

        Plan plan = planResponse.toEntity();

        param.put("userId", userId);
        param.put("title", title);
        param.put("context", context);
        param.put("textColor", textColor);
        param.put("backgroundColor", backgroundColor);
        param.put("allDay", allDay);
        param.put("plan", plan1);

        //when
        eventService.eventSave(param, file);
        EventResponse findByUserId = eventService.findAllByUserId(userId).get(0);

        //then
        Assertions.assertThat(findByUserId.getTitle()).isEqualTo(title);
        Assertions.assertThat(findByUserId.getContext()).isEqualTo(context);
    }

    @Order(2)
    @Test
    void 인증_수정_파일이_NULL() throws IOException, NoSuchAlgorithmException {
        //given
        Map<String, Object> param = new HashMap<>();

        String userId = "testId";
        EventResponse eventResponse = eventService.findAllByUserId(userId).get(0);
        Long id = eventResponse.getId();
        String updateTestTitle = "updateTestTitle";
        String context = "testContext";
        String textColor = "#02343f";
        String backgroundColor = "#02343f";
        Boolean allDay = true;
        byte[] data = new byte[]{1, 2, 3, 4};
        InputStream stream = new ByteArrayInputStream(data);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", stream);
        MockMultipartFile file1 = null;

        param.put("id", id);
        param.put("userId", userId);
        param.put("title", updateTestTitle);
        param.put("context", context);
        param.put("textColor", textColor);
        param.put("backgroundColor", backgroundColor);
        param.put("allDay", allDay);
        //when
        eventService.eventUpdate(param, file1, id);
        String updateEvent = eventService.findById(id).getTitle();
        //then
        Assertions.assertThat(updateEvent).isEqualTo(updateTestTitle);
    }

    @Order(3)
    @Test
    void 인증_삭제() {
        //given
        String userId = "testId";
        Long id = eventService.findAllByUserId(userId).get(0).getId();
        String fileById = eventService.findFileById(id);
        //when
        eventService.delete(id);
        s3Service.delete(fileById);

        EventResponse byId = eventService.findById(id);
        //then
        Assertions.assertThat(byId).isNull();
    }
}