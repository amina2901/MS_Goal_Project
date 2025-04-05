package com.webdis.ms_goal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoalRepository extends JpaRepository<Goal,Integer> {
    @Query("select g from Goal g where g.goalName like :goalName")
    public Page<Goal> GoalByGoalName(@Param("goalName") String n, Pageable pageable);
}
