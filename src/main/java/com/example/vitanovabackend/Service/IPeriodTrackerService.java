package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;

import java.util.List;


public interface IPeriodTrackerService {
    PeriodTracker AddPeriodInformation(PeriodTracker periodTracker);
    void deletePeriodInformation(long idPeriod);
    List<PeriodTracker> getPeriodTracker();
    PeriodTracker UpdatePeriodInformation(PeriodTracker UpdatedperiodTracker , long idPeriod );
    String archivePeriod(Long idPeriod);
    List<PeriodTracker> searchArchivedPeriodsForUser( Long idUser);

}

