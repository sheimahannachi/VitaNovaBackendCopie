package com.example.vitanovabackend.Service;


import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Repositories.PersonalGoalsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonalGoalsService implements IPersonalGoalsService{
    PersonalGoalsRepository personalGoalsRepository;

    @Override
    public PersonalGoals AddPersonalGoal (PersonalGoals personalGoals){
        return personalGoalsRepository.save(personalGoals);
    }

    @Override
    public int DeleteGoal(Long Id){
        PersonalGoals toDelete=personalGoalsRepository.findById(Id).get();
        if(toDelete!=null){
            personalGoalsRepository.delete(toDelete);
            return 1;       }

        else return 0;


    }
    @Override
    public PersonalGoals updateGoal(PersonalGoals personalGoals) {
        PersonalGoals existingGoal = personalGoalsRepository.findById(personalGoals.getIdPG()).get();

        if (existingGoal != null) {
            existingGoal=personalGoals;
            return personalGoalsRepository.save(existingGoal);
        }
        else return  null;
    }

    @Override
    public List<PersonalGoals> GetGoals() {
        return personalGoalsRepository.findAll();
    }

}
