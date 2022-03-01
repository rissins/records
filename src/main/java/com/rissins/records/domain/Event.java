package com.rissins.records.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicUpdate
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;

    private String title;

    private String context;

    private String textColor;

    private String backgroundColor;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    private String userId;

    @CreatedDate
    @LastModifiedDate
    private LocalDateTime start;

    @CreatedDate
    @LastModifiedDate
    private LocalDateTime end;

    private Boolean allDay;

    @Column(columnDefinition = "TEXT")
    private String file;

}
