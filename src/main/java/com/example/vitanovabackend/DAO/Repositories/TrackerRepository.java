package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.Tracker;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker,Long> {

  //  List<Tracker> findByDateBetweenAndPersonalGoalsUserIdUser(LocalDate lastWeek,LocalDate today,long id);
 //   List<Tracker> findByDateBetweenAndPersonalGoalsUser(LocalDate lastWeek, LocalDate today, User user);

  @Query("SELECT t FROM Tracker t WHERE t.date BETWEEN :lastWeek AND :today AND t.personalGoals.user = :user")
  List<Tracker> findByDateBetweenAndPersonalGoalsUser(@Param("lastWeek") LocalDate lastWeek, @Param("today") LocalDate today, @Param("user") User user);


  List<Tracker> findTrackerByDateBetweenAndPersonalGoals(LocalDate lastWeek, LocalDate Today, PersonalGoals personalGoals);

}
