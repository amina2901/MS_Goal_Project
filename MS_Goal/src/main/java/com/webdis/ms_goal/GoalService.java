package com.webdis.ms_goal;

import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService implements IGoalService {
    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal addGoal(Goal g) {
        return goalRepository.save(g);
    }

    @Override
    public List<Goal> getAll() {
        return goalRepository.findAll();
    }

    @Override
    public Goal updateGoal(int id, Goal newGoal) {
        Optional<Goal> existingGoalOpt = goalRepository.findById(id);

        if (existingGoalOpt.isPresent()) {
            Goal existingGoal = existingGoalOpt.get();
            existingGoal.setGoalName(newGoal.getGoalName());
            existingGoal.setStatus(newGoal.getStatus());
            existingGoal.setUpdatedAt(LocalDateTime.now());
            existingGoal.setTargetTime(newGoal.getTargetTime());
            return goalRepository.save(existingGoal);
        } else {
            throw new RuntimeException("Goal avec id " + id + " non trouvé");
        }
    }

    @Override
    public String deleteGoal(int id) {
        Optional<Goal> goalOpt = goalRepository.findById(id);

        if (goalOpt.isPresent()) {
            goalRepository.deleteById(id);
            return "goal supprimé avec succès";
        } else {
            throw new RuntimeException("goal avec id " + id + " non trouvé");
        }
    }
}
