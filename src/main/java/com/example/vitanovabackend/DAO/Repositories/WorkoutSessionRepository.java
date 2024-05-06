package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {
        @Query("SELECT FUNCTION('YEAR', ws.time_start), FUNCTION('MONTH', ws.time_start), COUNT(ws) " +
                "FROM WorkoutSession ws " +
                "WHERE ws.user.idUser = :userId " +
                "GROUP BY FUNCTION('YEAR', ws.time_start), FUNCTION('MONTH', ws.time_start)")
        List<WorkoutSession> getUserTrainingStatistics(long userId);
        List<WorkoutSession> findByUser_IdUser(long iduser);
    }
