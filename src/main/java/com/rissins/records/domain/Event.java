package com.rissins.records.domain;

import com.rissins.records.dto.EventResponse;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

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

    @Column
    private String title;

    @Column
    private String context;

    @Column
    private String textColor;

    @Column
    private String backgroundColor;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
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

    public void changeTitle(String title) {
        this.title = title;
    }
}
