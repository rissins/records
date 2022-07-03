package com.rissins.records.repository;

import com.rissins.records.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>, QuerydslPredicateExecutor<Plan> {

    List<Plan> findAllByUserId(String userId);
}
