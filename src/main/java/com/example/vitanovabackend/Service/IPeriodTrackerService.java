package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;

import java.time.LocalDate;

import java.util.List;


public interface IPeriodTrackerService {
    PeriodTracker AddPeriodInformation(PeriodTracker periodTracker);

    JournalEntry AddJournal(JournalEntry journalEntry);
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
     List<Exercise> getPeriodExercises();
    List<Food> getPeriodFood();
    List<LocalDate> calculateFertileWindow(PeriodTracker periodTracker);



}

