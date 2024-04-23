package com.example.vitanovabackend.DAO.Repositories;


import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.Intensity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    @Query("SELECT e FROM Exercise e WHERE e.archived = false")
    Page<Exercise> findActiveExercises(Pageable pageable);
    Page<Exercise> findByBodypartAndTitle(String bodyParts, String searchText, Pageable pageable);

    Page<Exercise> findByBodypart(String bodypart, Pageable pageable);

    Page<Exercise> findByTitle(String searchText,Pageable pageable);
    @Query("SELECT e FROM Exercise e LEFT JOIN UserRating ur ON e.id = ur.exercise.id GROUP BY e.id ORDER BY AVG(ur.rate) DESC")
    Page<Exercise> findExercisesOrderByAverageRating(Pageable pageable);

    List<Exercise> findByTitle(String searchText);
    List<Exercise> findAllByIntensity(Intensity LOW);

}

