package com.webdis.ms_goal;

import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Override
    public Goal getGoalById(int id) {
        // Utilisation de findById pour récupérer l'objectif par ID
        Optional<Goal> goal = goalRepository.findById(id);

        // Si l'objectif existe, on le renvoie, sinon on retourne null
        return goal.orElse(null);
    }
    public Map<String, Object> calculateGlobalProgress() {
        List<Goal> goals = goalRepository.findAll();
        int totalGoals = goals.size();
        int completedGoals = (int) goals.stream().filter(goal -> Status.Completed.equals(goal.getStatus())).count();
        int inProgressGoals = (int) goals.stream().filter(goal -> Status.InProgress.equals(goal.getStatus())).count();
        int notStartedGoals = (int) goals.stream().filter(goal -> Status.NotStarted.equals(goal.getStatus())).count();

        double completionRate = totalGoals > 0 ? (completedGoals * 100.0) / totalGoals : 0.0;
        completionRate = Math.round(completionRate * 100.0) / 100.0; // arrondi à 2 chiffres après la virgule

        return Map.of(
                "totalGoals", totalGoals,
                "completedGoals", completedGoals,
                "inProgressGoals", inProgressGoals,
                "notStartedGoals", notStartedGoals,
                "completionRate", completionRate
        );
    }
    public Map<Long, Integer> calculateStreaks() {
        List<Goal> goals = goalRepository.findAll();
        Map<Long, Integer> streaks = new HashMap<>();

        for (Goal goal : goals) {
            LocalDateTime lastUpdate = goal.getUpdatedAt();
            if (lastUpdate != null && lastUpdate.isAfter(LocalDateTime.now().minusDays(7))) {
                streaks.put((long) goal.getId(), streaks.getOrDefault(goal.getId(), 0) + 1);
            }
        }
        return streaks;
    }


}
