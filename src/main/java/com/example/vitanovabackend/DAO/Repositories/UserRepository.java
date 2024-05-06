package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.periodTracker IS NOT NULL")
    List<User> findByPeriodNotNull();

     List<User> findAllByFirstName(String firstName);
     List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
     List<User> findAllByLastName(String lastName);
    User findByPhone(String Phone);
    User findByEmail(String email);
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByPersonalGoals(PersonalGoals personalGoals);

}