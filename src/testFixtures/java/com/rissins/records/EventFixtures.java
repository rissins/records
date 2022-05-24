package com.rissins.records;

import com.rissins.records.domain.Event;
import com.rissins.records.domain.Plan;
import com.rissins.records.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static com.rissins.records.common.CommonFixtures.*;
import static com.rissins.records.common.CommonFixtures.CONTENT;

public class EventFixtures {


    public Event getEvent(User user, Plan plan, String file) throws IOException, NoSuchAlgorithmException {
        return Event.builder()
                .id(ID)
                .user(user)
                .plan(plan)
                .userId(USER_ID)
                .textColor(TEXT_COLOR)
                .backgroundColor(BACKGROUND_COLOR)
                .allDay(ALL_DAY)
                .title(TITLE)
                .context(CONTENT)
                .file(file)
                .build();
    }

    public MultipartFile getFile() {
        return IMAGE;
    }
}
