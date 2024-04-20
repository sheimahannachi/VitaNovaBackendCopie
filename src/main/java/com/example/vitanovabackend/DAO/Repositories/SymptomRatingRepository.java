package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.SymptomRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SymptomRatingRepository extends JpaRepository<SymptomRating,Long> {
    List<SymptomRating> findByPeriodTrackerIdPeriod(Long idPeriod);


}