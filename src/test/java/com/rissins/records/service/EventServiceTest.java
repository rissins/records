package com.rissins.records.service;

import com.rissins.records.EventFixtures;
import com.rissins.records.PlanFixtures;
import com.rissins.records.UserFixtures;
import com.rissins.records.domain.Plan;
import com.rissins.records.domain.User;
import com.rissins.records.domain.Event;
import com.rissins.records.dto.EventResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Transactional
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
    @Rollback(value = false)
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
        List<Event> allWithEventWithFetchJoin = eventService.findAllWithEventWithFetchJoin();

        //then
        Assertions.assertThat(allWithEventWithFetchJoin).isNotNull();
    }

    @Order(2)
    @Test
    void 인증_수정_파일이_NULL() {
        //given
        EventResponse eventResponseWithUpdateTitleAndWithoutFile = eventFixtures.getEventResponseWithUpdateTitleAndWithoutFile();
        String userId = eventResponseWithUpdateTitleAndWithoutFile.getUserId();
        //when
        Long id = eventService.findAllByUserId(userId).get(0).getId();
        Event byIdss = eventService.findByIdss(id);
        byIdss.updateInfo(eventResponseWithUpdateTitleAndWithoutFile);
        String updateEventTitle = eventService.findAllByUserId(userId).get(0).getTitle();
        EventResponse findUpdateEventById = eventService.findAllByUserId(userId).get(0);
        //then
        Assertions.assertThat(updateEventTitle).isEqualTo(eventResponseWithUpdateTitleAndWithoutFile.getTitle());
        Assertions.assertThat(findUpdateEventById.getFile()).isNotEmpty();
    }

    @Order(3)
    @Test
    @Rollback(value = false)
    void 인증_삭제() {
        //given
        String userId = eventFixtures.getEventResponseWithUpdateTitleAndWithoutFile().getUserId();
        Long id = eventService.findAllByUserId(userId).get(0).getId();
        String fileById = eventService.findFileById(id);
        Long planId = planService.findAllByUserId(userId).get(0).getId();
        List<Long> planIds = new ArrayList<>(Arrays.asList(planId));
        Long findIdByUserId = userService.findByUserId(userId).getId();
        //when
        userService.deleteById(findIdByUserId);
        eventService.delete(id);
        planService.deleteByIds(planIds);
        s3Service.delete(fileById);

        List<EventResponse> allByUserId = eventService.findAllByUserId(userId);
        //then
        Assertions.assertThat(allByUserId).isEmpty();
    }
}