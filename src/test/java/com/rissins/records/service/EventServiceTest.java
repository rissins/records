package com.rissins.records.service;

import com.rissins.records.EventFixtures;
import com.rissins.records.PlanFixtures;
import com.rissins.records.UserFixtures;
import com.rissins.records.domain.Plan;
import com.rissins.records.domain.User;
import com.rissins.records.dto.PlanResponse;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import com.rissins.records.dto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    UserService userService;

    private UserFixtures userFixtures;
    private PlanFixtures planFixtures;
    private EventFixtures eventFixtures;
    @BeforeEach
    void setUp() {
        this.userFixtures = new UserFixtures();
        this.planFixtures = new PlanFixtures();
        this.eventFixtures = new EventFixtures();
    }

    @Order(1)
    @Test
    void 인증_추가() throws IOException, NoSuchAlgorithmException {
        //given
        Plan plan = planFixtures.getPlan();
        User user = userFixtures.getUser();
        String uploadFileUrl = s3Service.upload(eventFixtures.getFile());

        userService.signUp(user.toResponse());
        planService.save(plan.toResponse());
        Plan findPlanByUserId = planService.findAllByUserId(user.getUserId()).get(0);
        User findUserByFixturesUserId = userService.findByUserId(user.getUserId());

        Event event = eventFixtures.getEvent(findUserByFixturesUserId, findPlanByUserId, uploadFileUrl);

        //when
        eventService.save(event);
        List<EventResponse> findEventsByUserId = eventService.findAllByUserId(event.getUserId());

        //then
        Assertions.assertThat(findEventsByUserId).isNotNull();
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