package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserExerciseRatingRepository extends JpaRepository<UserRating,Long> {
    @Query("SELECT ur FROM UserRating ur WHERE ur.exercise.id = :exerciseId")
    List<UserRating> findByExerciseId(long exerciseId);
}
