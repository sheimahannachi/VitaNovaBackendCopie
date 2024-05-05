package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;

import java.util.List;

public interface IPersonalGoalsService {

    PersonalGoals AddPersonalGoal (PersonalGoals personalGoals);
    User AddPersonalGoalAndAffect (PersonalGoals personalGoals , long userId);

    int DeleteGoal(Long Id);

    PersonalGoals updateGoal(PersonalGoals personalGoals);
    public List<PersonalGoals> GetGoals();
    
}
