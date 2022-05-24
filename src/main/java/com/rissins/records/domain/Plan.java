package com.rissins.records.domain;

import com.rissins.records.dto.PlanResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String context;

    private String userId;

    public PlanResponse toResponse() {
        return PlanResponse.builder()
                .id(id)
                .title(title)
                .context(context)
                .userId(userId)
                .build();
    }
}
