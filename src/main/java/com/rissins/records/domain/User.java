package com.rissins.records.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_uid")
    private Long uid;

//    @Column(name = "user_id")
//    @OneToMany(mappedBy = "user")
//    private Set<Event> event;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_password")
    private String userPassword;


}
