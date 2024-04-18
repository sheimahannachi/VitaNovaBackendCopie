package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface PeriodTrackerRepository extends JpaRepository<PeriodTracker,Long> {

     List<PeriodTracker> findByUserAndArchive(User user, boolean archive);
    List<PeriodTracker>findByArchiveFalse();

}
