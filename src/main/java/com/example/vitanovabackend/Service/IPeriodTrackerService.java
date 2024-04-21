package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;

import com.example.vitanovabackend.DAO.Entities.SymptomRating;
import com.example.vitanovabackend.DAO.Entities.User;

import java.time.LocalDate;

import java.util.List;


public interface IPeriodTrackerService {
    PeriodTracker AddPeriodInformation(PeriodTracker periodTracker);
    void deletePeriodInformation(long idPeriod);
    List<PeriodTracker> getPeriodTracker();
    PeriodTracker UpdatePeriodInformation(PeriodTracker UpdatedperiodTracker , long idPeriod );
    String archivePeriod(Long idPeriod);
    List<PeriodTracker> searchArchivedPeriodsForUser( Long idUser);

    LocalDate calculateOvulationDate (PeriodTracker periodTracker);
    LocalDate calculateNextPeriodDate (PeriodTracker periodTracker);
    String calculateCyclePhase (PeriodTracker periodTracker);
    List<User> findByPeriodNotNull();
    List<PeriodTracker> getNonArchivedPeriodTrackers();
    PeriodTracker getPeriodTrackerById (long idPeriod);
    List<SymptomRating> getSymptomsAndRatingsForPeriod(long periodId);
    List<LocalDate> calculateFertileWindow(PeriodTracker periodTracker);


}

