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
    public List<Exercise> getExercisesByBodypartAndIntensity(String bodyPart,String intensity) ;

    public List<Exercise> getExercisesByTypeExAndIntensity(String typeEx,String intensity) ;
    List<Exercise> findByBodypartAndTitle(String bodyParts, String searchText);

    List<Exercise> findByBodypart(String bodyParts);

    List<Exercise> findByTitle(String searchText);
    List<Exercise> findAllByIntensity(Intensity LOW);
}
