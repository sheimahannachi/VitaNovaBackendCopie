package com.example.vitanovabackend.Service;


import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.PersonalGoalsRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonalGoalsService implements IPersonalGoalsService{
    PersonalGoalsRepository personalGoalsRepository;

UserRepository userRepository;
    @Override
    public PersonalGoals AddPersonalGoal (PersonalGoals personalGoals){
        return personalGoalsRepository.save(personalGoals);
    }

    @Override
    public User AddPersonalGoalAndAffect(PersonalGoals personalGoals, long userId) {
if(userRepository.findById(userId).isPresent()){
User user=userRepository.findById(userId).get();
        personalGoalsRepository.save(personalGoals);
        user.setPersonalGoals(personalGoals);
        user.setScore(user.getScore()+1);
        return userRepository.save(user);}
return null;
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
