package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.PersonalGoalsRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.Service.IPersonalGoalsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/PG")
public class PersonalGoalsController {

IPersonalGoalsService personalGoalsService;


    @PostMapping("/AddGoal")
    public PersonalGoals addGoal(@RequestBody PersonalGoals Pg) {
        return personalGoalsService.AddPersonalGoal(Pg);
    }



}
