package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PeriodTrackerService implements IPeriodTrackerService {
    PeriodTrackerRepository periodTrackerRepository;
    UserRepository userRepository;
    ExerciseRepository exerciseRepository ;
    FoodRepository foodRepository;
    private SymptomRatingRepository symptomRatingRepository;
    JournalRepository journalRepository;
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
    public LocalDate calculateNextPeriodDate(PeriodTracker periodTracker) {
        // Retrieve period information
        LocalDate lastPeriodStartDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();
        int periodLength = periodTracker.getPeriodLength();

        // Consider the influence of medications, symptoms, and mood on cycle length
        double medicationFactor = calculateMedicationFactor(periodTracker.getMedications());
        double symptomsFactor = calculateSymptomsFactor(periodTracker.getSymptomRatings());
        double moodFactor = calculateMoodFactor(periodTracker.getMood());

        // Adjust cycle length based on factors
        int adjustedCycleLength = (int) (cycleLength * medicationFactor * symptomsFactor * moodFactor);

        // Calculate the next period start date
        LocalDate currentDate = LocalDate.now();
        long daysSinceLastPeriodStart = ChronoUnit.DAYS.between(lastPeriodStartDate, currentDate);
        int remainingDaysInCycle = adjustedCycleLength - (int) (daysSinceLastPeriodStart % adjustedCycleLength);

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

    // Function to calculate the medication factor based on selected medications
    private double calculateMedicationFactor(String selectedMedication) {
        // Adjust cycle length based on selected medications
        switch (selectedMedication) {
            case "Oral Contraceptive Pills":
                // If Oral Contraceptive Pills are selected, reduce cycle length by 10%
                return 0.90;
            case "Aspirin":
                // If Aspirin is selected, reduce cycle length by 5%
                return 0.95;
            case "Ibuprofen":
                // If Ibuprofen is selected, reduce cycle length by 5%
                return 0.95;
            case "Anti Depressant Medication":
                // If Anti Depressant Medication is selected, reduce cycle length by 5%
                return 0.95;
            case "Weight loss medication":
                // If Weight loss medication is selected, reduce cycle length by 5%
                return 0.95;
            case "Chemo therapy":
                // If Chemo therapy is selected, lengthen cycle length by 10%
                return 1.10;
            case "Steroids":
                // If Steroids are selected, reduce cycle length by 5%
                return 0.95;
            case "Other Forms of contraceptive":
                // If Other Forms of contraceptive are selected, reduce cycle length by 5%
                return 0.95;
            case "Any":
                // If "Any" medication is selected, no adjustment to cycle length
                return 1.0;
            default:
                // Default factor if medication is not recognized
                return 1.0;
        }
    }

    // Function to calculate the symptoms factor based on symptom ratings
    private double calculateSymptomsFactor(List<SymptomRating> symptomRatings) {
        // Adjust cycle length based on symptom severity
        double severitySum = symptomRatings.stream().mapToDouble(SymptomRating::getRating).sum();
        // Normalize severity sum to a factor between 0.9 and 1.1
        return 0.9 + (severitySum / (symptomRatings.size() * 10.0)) * 0.2;
    }

    // Function to calculate the mood factor based on selected mood
    private double calculateMoodFactor(String selectedMood) {
        // Adjust cycle length based on selected mood
        switch (selectedMood) {
            case "super-happy":
                return 1.05;
            case "neutral":
                return 1.0;
            case "super-sad":
                return 0.95;
            default:
                return 1.0; // Default factor if mood is not recognized
        }
    }

    // Function to calculate the ovulation date based on period information and medications
    @Override
    public LocalDate calculateOvulationDate(PeriodTracker periodTracker) {
        // Retrieve period information
        LocalDate lastPeriodStartDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();

        // Adjust cycle length based on selected medications
        double medicationFactor = calculateMedicationFactorr(periodTracker.getMedications());
        cycleLength = (int) Math.round(cycleLength * medicationFactor);

        // Calculate the ovulation date
        LocalDate ovulationDate = lastPeriodStartDate.plusDays(cycleLength - 14);
        return ovulationDate;
    }
    // Function to calculate the medication factor based on selected medications
    private double calculateMedicationFactorr(String selectedMedication) {
        // Adjust cycle length based on selected medications
        switch (selectedMedication) {
            case "Oral Contraceptive Pills":
                // If Oral Contraceptive Pills are selected, reduce cycle length by 10%
                return 0.90;
            case "Aspirin":
            case "Ibuprofen":
            case "Anti Depressant Medication":
            case "Weight loss medication":
            case "Steroids":
            case "Other Forms of contraceptive":
                // If any of these medications are selected, reduce cycle length by 5%
                return 0.95;
            case "Chemo therapy":
                // If Chemo therapy is selected, lengthen cycle length by 10%
                return 1.10;
            case "Any":
                // If "Any" medication is selected, no adjustment to cycle length
                return 1.0;
            default:
                // Default factor if medication is not recognized
                return 1.0;
        }
    }


    @Override
    public List<SymptomRating> getSymptomsAndRatingsForPeriod(long idPeriod) {
        // Fetch period tracker by ID
        PeriodTracker periodTracker = periodTrackerRepository.findById(idPeriod)
                .orElseThrow(() -> new NoSuchElementException("Period tracker not found with id: " + idPeriod));

        // Get symptom ratings associated with the period tracker
        List<SymptomRating> symptomRatings = periodTracker.getSymptomRatings();

        return symptomRatings;
    }
    public List<Exercise> getPeriodExercises() {
        return exerciseRepository.findAllByIntensity(Intensity.LOW);
    }
    public List<Food> getPeriodFood() {
        return foodRepository.findByVitaminCGreaterThanAndVitaminB6GreaterThanAndCalciumGreaterThanAndVitaminEGreaterThan(
                0.1, 0.1, 0.1, 0.1
        );
    }
    @Override
    public List<LocalDate> calculateFertileWindow(PeriodTracker periodTracker) {
        LocalDate lastPeriodStartDate = LocalDate.parse(periodTracker.getStartDate());
        int cycleLength = periodTracker.getCycleLength();

        // Adjust cycle length based on medications
        double medicationFactor = calculateMedicationFactor(periodTracker.getMedications());
        cycleLength = (int) Math.round(cycleLength * medicationFactor);

        // Ovulation typically occurs about 14 days before the next period
        LocalDate ovulationDate = lastPeriodStartDate.plusDays(cycleLength - 14);

        // Fertile window starts 5 days before ovulation and ends 1 day after
        LocalDate fertileWindowStart = ovulationDate.minusDays(5);
        LocalDate fertileWindowEnd = ovulationDate.plusDays(1);

        // Return the start and end dates of the fertile window
        return Arrays.asList(fertileWindowStart, fertileWindowEnd);
    }
    @Override
    public JournalEntry AddJournal(JournalEntry journalEntry) {


        return journalRepository.save(journalEntry);
    }

}




