package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.PeriodTrackerRepository;

import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class PeriodTrackerService implements IPeriodTrackerService {
    PeriodTrackerRepository periodTrackerRepository;
    UserRepository userRepository;
    @Override
    public PeriodTracker AddPeriodInformation(PeriodTracker periodTracker) {

        return periodTrackerRepository.save(periodTracker);
    }

    @Override
    public String archivePeriod(Long idPeriod) {
        PeriodTracker period = periodTrackerRepository.findById(idPeriod).orElse(null);
        if (period != null) {
            period.setArchive(true);
            periodTrackerRepository.save(period);
            return "archived";
        } else {
            return "not found";
        }
    }
    @Override
    public void deletePeriodInformation(long idPeriod){

         periodTrackerRepository.deleteById(idPeriod);
    }
    @Override
    public PeriodTracker UpdatePeriodInformation(PeriodTracker UpdatedperiodTracker , long idPeriod ) {

        PeriodTracker periodTracker = periodTrackerRepository.findById(idPeriod).orElse(null);

        if (periodTracker == null)
            return null;

        UpdatedperiodTracker.setIdPeriod(idPeriod);
        return periodTrackerRepository.save(UpdatedperiodTracker);
    }

    @Override
    public List<PeriodTracker> getPeriodTracker() {
        return periodTrackerRepository.findAll();
    }
    @Override
    public List<PeriodTracker> searchArchivedPeriodsForUser(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        if (user != null) {
            return periodTrackerRepository.findByUserAndArchive(user, true);
        } else {
            return Collections.emptyList();
        }

}}

