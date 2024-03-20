package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.PeriodTrackerRepository;

import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;




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
    public PeriodTracker getPeriodTrackerById (long idPeriod) {
        return periodTrackerRepository.findById(idPeriod)
                .orElseThrow(() -> new NoSuchElementException("Period tracker not found with id: " + idPeriod));
    }
    @Override

    public List<PeriodTracker> searchArchivedPeriodsForUser(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        if (user != null) {
            return periodTrackerRepository.findByUserAndArchive(user, true);
        } else {
            return Collections.emptyList();
        }

}
    @Override
    public List<PeriodTracker> getNonArchivedPeriodTrackers() {
        return periodTrackerRepository.findByArchiveFalse();
    }
    @Override
    public List<User> findByPeriodNotNull() {
        return userRepository.findByPeriodNotNull();
    }
    @Override
    public String calculateCyclePhase (PeriodTracker periodTracker) {
        // Retrieve period information
        LocalDate startDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();
        int periodLength = periodTracker.getPeriodLength();

        // Calculate the current day in the cycle
        LocalDate currentDate = LocalDate.now();
        long daysSinceStart = ChronoUnit.DAYS.between(startDate, currentDate);
        int currentDayInCycle = (int) (daysSinceStart % cycleLength) + 1;

        // Determine the cycle phase based on the current day in the cycle and period length
        if (currentDayInCycle <= periodLength) {
            return "Menstrual Phase";
        } else if (currentDayInCycle <= (periodLength + 5)) {
            return "Ovulation Phase";
        } else {
            return "Luteal Phase";
        }
    }
    @Override
    public LocalDate calculateNextPeriodDate (PeriodTracker periodTracker) {
        // Retrieve period information
        LocalDate lastPeriodStartDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();
        int periodLength = periodTracker.getPeriodLength();

        // Calculate the next period start date
        LocalDate currentDate = LocalDate.now();
        long daysSinceLastPeriodStart = ChronoUnit.DAYS.between(lastPeriodStartDate, currentDate);
        int remainingDaysInCycle = cycleLength - (int) (daysSinceLastPeriodStart % cycleLength);

        // Check if the next period has already started
        if (remainingDaysInCycle <= periodLength) {
            // If the remaining days in the cycle are less than or equal to the period length,
            // the next period has already started.
            return currentDate.plusDays(remainingDaysInCycle);
        } else {
            // If the next period hasn't started yet, calculate the days until the next period starts
            return currentDate.plusDays(remainingDaysInCycle - periodLength);
        }
    }
    @Override
    public LocalDate calculateOvulationDate (PeriodTracker periodTracker) {
        // Retrieve period information
        LocalDate lastPeriodStartDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();
        // Calculate the ovulation date
        LocalDate ovulationDate = lastPeriodStartDate.plusDays(cycleLength - 14);
        return ovulationDate;
    }
}




