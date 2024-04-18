package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;

import java.util.List;

public interface IPersonalGoalsService {

    PersonalGoals AddPersonalGoal (PersonalGoals personalGoals);
    int DeleteGoal(Long Id);

    PersonalGoals updateGoal(PersonalGoals personalGoals);
    public List<PersonalGoals> GetGoals();
    
}
