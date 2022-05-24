package com.rissins.records.repository;

import com.rissins.records.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUserId(String userId);

    List<Event> findByIdIn(List<Long> ids);

    @Query("SELECT distinct e FROM Event e join fetch e.plan")
    List<Event> findAllWithEventWithFetchJoin();
}
