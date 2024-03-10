package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciseRatingRepository extends JpaRepository<UserRating,Long> {
}
