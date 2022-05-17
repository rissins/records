package com.rissins.records.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.rissins.records.domain.Event;
import com.rissins.records.domain.Plan;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String context;
    private String textColor;
    private String backgroundColor;
    private String userId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime start;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime end;

    private Boolean allDay;
    private String file;

    private Plan plan;

    public Event toEntity() {
        return Event.builder()
                .id(id)
                .plan(plan)
                .userId(userId)
                .title(title)
                .context(context)
                .backgroundColor(backgroundColor)
                .textColor(textColor)
                .allDay(allDay)
                .file(file)
                .build();
    }
}
