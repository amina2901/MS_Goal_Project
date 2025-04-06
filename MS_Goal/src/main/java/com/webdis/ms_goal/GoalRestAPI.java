package com.webdis.ms_goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goals")
public class GoalRestAPI {
    private String title="Hello MS Goal";
    @Autowired
    private GoalService goalService;

    @Autowired
    private API_AITimePlannerService aiTimePlannerService;

    @RequestMapping("/hello")
    public String sayHello(){
        return title;
    }

    @Autowired
    private IGoalService iGoalService;

    @GetMapping
    public ResponseEntity<List<Goal>> getAll(){
        return new ResponseEntity(iGoalService.getAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        return new ResponseEntity<>(iGoalService.addGoal(goal), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Goal> updateGoal(@PathVariable(value = "id") int id,
                                                   @RequestBody Goal goal){
        return new ResponseEntity<>(iGoalService.updateGoal(id, goal),
                HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteGoal(@PathVariable(value = "id") int id){
        return new ResponseEntity<>(iGoalService.deleteGoal(id), HttpStatus.OK);
    }

    @GetMapping("/{goalId}/progress")
    public ResponseEntity<Goal> getGoalProgress(@PathVariable int goalId) {
        Goal goal = iGoalService.getGoalById(goalId);
        if (goal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goal);
    }
    @GetMapping("/progress/analytics")
    public ResponseEntity<Map<String, Object>> getProgressAnalytics() {
        Map<String, Object>analytics = iGoalService.calculateGlobalProgress();
        return ResponseEntity.ok(analytics);
    }
    @PostMapping("/{id}/ai-timeplan")
    public AiTimePlanResponse generateAiPlan(@PathVariable int id, @RequestBody AIPlanningRequest request) {
        Goal goal = iGoalService.getGoalById(id);
        if (goal == null) {
            // Retourner un message d'erreur ou un code HTTP 404 ou 400 selon le cas
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found");
        }
        return aiTimePlannerService.generatePlan(goal, request);
    }



}
