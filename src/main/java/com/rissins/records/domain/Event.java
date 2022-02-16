package com.rissins.records.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Setter
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

    private String borderColor;



}
