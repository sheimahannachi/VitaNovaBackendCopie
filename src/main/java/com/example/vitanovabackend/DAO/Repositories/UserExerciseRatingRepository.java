package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserExerciseRatingRepository extends JpaRepository<UserRating,Long> {
    @Query("SELECT ur FROM UserRating ur WHERE ur.exercise.id = :exerciseId")
    List<UserRating> findByExerciseId(long exerciseId);
    @Query("SELECT ur FROM UserRating ur WHERE ur.user = :user AND ur.exercise = :exercise")
    Optional<UserRating> findByUserAndExercise(User user, Exercise exercise);
}
