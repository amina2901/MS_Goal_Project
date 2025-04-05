package com.webdis.ms_goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalRestAPI {
    private String title="Hello MS Goal";

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
}
