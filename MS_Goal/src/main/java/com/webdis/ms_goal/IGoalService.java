package com.webdis.ms_goal;

import java.util.List;

public interface IGoalService {
    public Goal addGoal(Goal g);
    public List<Goal> getAll();
    Goal updateGoal(int id, Goal newGoal);
    String deleteGoal(int id);
}
