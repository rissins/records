package com.rissins.records.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private Long uid;

    private String id;

    private String title;

    private String context;

    private String textColor;

    private String backgroundColor;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
    private String userId;

    @CreatedDate
    private String start;

    @CreatedDate
    private String end;


    @PrePersist
    public void onPrePersist() {
        this.start = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"));
        this.end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
    }

//    @PrePersist
//    public void onPreUpdate() {
//
//    }

    @Override
    public String toString() {
        return "Event{" +
                "uid=" + uid +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", textColor='" + textColor + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
//                ", createdDate=" + createdDate +
                '}';
    }
}
