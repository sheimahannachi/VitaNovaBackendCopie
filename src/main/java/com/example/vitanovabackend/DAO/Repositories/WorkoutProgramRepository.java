package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Intensity;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutProgramRepository extends JpaRepository<WorkoutProgram,Long> {
    @Query("SELECT w FROM WorkoutProgram w WHERE w.archived = false")
    Page<WorkoutProgram>findActiveWorkoutPlan(Pageable pageable);
    WorkoutProgram findByIntensity(Intensity intensity);

}
