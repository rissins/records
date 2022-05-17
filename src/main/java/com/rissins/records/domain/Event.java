package com.rissins.records.domain;

import com.rissins.records.dto.EventResponse;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "event")
@DynamicUpdate
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String externalId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column
    private String title;

    @Column
    private String context;

    @Column
    private String textColor;

    @Column
    private String backgroundColor;

    @Column
    private String userId;

    @Column
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime start;

    @Column
    @CreatedDate
    @LastModifiedDate
    private LocalDateTime end;

    @Column
    private Boolean allDay;

    @Column(columnDefinition = "TEXT")
    private String file;


    public void updateInfo(EventResponse eventResponse) {
        if (eventResponse.getFile() != null) {
            this.file = eventResponse.getFile();
        }
        this.title = eventResponse.getTitle();
        this.context = eventResponse.getContext();
        this.textColor = eventResponse.getTextColor();
        this.backgroundColor = eventResponse.getBackgroundColor();
        this.userId = eventResponse.getUserId();
    }

    public EventResponse toResponse() {
        return EventResponse.builder()
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
