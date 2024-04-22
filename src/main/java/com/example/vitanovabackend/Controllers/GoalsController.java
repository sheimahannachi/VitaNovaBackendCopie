package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.IPersonalGoalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/goals")
public class GoalsController {
    @Autowired
IPersonalGoalsService iservices;

    @GetMapping("/all")
    public List<PersonalGoals> GetAllGoals() {

        System.out.println("getting all goals");
        return iservices.GetGoals();
    }

    @PostMapping("AddGoal")
    public User AddGoal(@RequestBody PersonalGoals personalGoals , @RequestParam("userId") long userId){
        System.out.println("adding");

        return (iservices.AddPersonalGoalAndAffect(personalGoals,userId));
    }


    @PutMapping("UpdateGoal")
    public PersonalGoals UpdateGoal(@RequestBody PersonalGoals personalGoals){
        return (iservices.updateGoal(personalGoals));
    }

    @DeleteMapping("DeleteGoal/{id}")
    public void DeleteGoal(@PathVariable("id") long id){
        iservices.DeleteGoal(id);
    }


}
