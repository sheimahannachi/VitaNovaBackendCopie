package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.Service.IPeriodTrackerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/PeriodTracker")
public class PeriodTrackerController {
    IPeriodTrackerService iPeriodTrackerService;

    @PostMapping("AddPeriodInformation")
    public PeriodTracker AddPeriodInformation(@RequestBody PeriodTracker periodTracker) {
        return iPeriodTrackerService.AddPeriodInformation(periodTracker);
    }

    @PostMapping("AddJournal")
    public JournalEntry AddJournal(@RequestBody JournalEntry journalEntry) {
        return iPeriodTrackerService.AddJournal(journalEntry);
    }
    @DeleteMapping ("deletePeriodinformation")
    public void deletePeriodInformation(@RequestParam long idPeriod ) {
        iPeriodTrackerService.deletePeriodInformation(idPeriod );
    }
    @PutMapping ("UpdatePeriodinformation")
    public PeriodTracker UpdatePeriodinformation(@RequestBody PeriodTracker UpdatedperiodTracker ,@RequestParam long idPeriod ) {
        return iPeriodTrackerService.UpdatePeriodInformation(UpdatedperiodTracker,idPeriod);
    }
    @GetMapping("getPeriodTracker")
    public List<PeriodTracker> getPeriodTracker(){
        return iPeriodTrackerService.getPeriodTracker();
    }

    @GetMapping("getPeriodTrackerById/{idPeriod}")
    public PeriodTracker getPeriodTrackerById(@PathVariable("idPeriod") long idPeriod) {
        return iPeriodTrackerService.getPeriodTrackerById(idPeriod);
    }
    @PutMapping("archivePeriod")
    public ResponseEntity<String> archivePeriod(@RequestParam Long idPeriod) {
        String result = iPeriodTrackerService.archivePeriod(idPeriod);
        return ResponseEntity.ok(result);
    }
    @GetMapping("ArchivedPeriods")
    public List<PeriodTracker>searchArchivedPeriodsForUser(@RequestParam long idUser){
        return iPeriodTrackerService.searchArchivedPeriodsForUser(idUser);
    }

    @GetMapping("/nonArchivedPeriodTrackers")
    public List<PeriodTracker> getNonArchivedPeriodTrackers() {
        return iPeriodTrackerService.getNonArchivedPeriodTrackers();
    }
    @GetMapping("/periodNotNull")
    public List<User> getUsersWithPeriodNotNull() {
        return iPeriodTrackerService.findByPeriodNotNull();
    }
    @PostMapping("/calculate-cycle-phase")
    public String calculateCyclePhase(@RequestBody PeriodTracker periodTracker) {
        return iPeriodTrackerService.calculateCyclePhase(periodTracker);
    }
    @GetMapping("/{idPeriod}/next-period-date")
    public ResponseEntity<LocalDate> getNextPeriodDate(@PathVariable long idPeriod) {
        // Retrieve PeriodTracker entity by ID
        PeriodTracker periodTracker = iPeriodTrackerService.getPeriodTrackerById(idPeriod);
        if (periodTracker == null) {
            // Return 404 Not Found if PeriodTracker entity is not found
            return ResponseEntity.notFound().build();
        }

        // Calculate the next period date
        LocalDate nextPeriodDate = iPeriodTrackerService.calculateNextPeriodDate(periodTracker);

        // Return the next period date in the response body
        return ResponseEntity.ok(nextPeriodDate);
    }



    @GetMapping("/{idPeriod}/ovulation-date")
    public ResponseEntity<LocalDate> calculateOvulationDate(@PathVariable Long idPeriod) {
        // Fetch PeriodTracker from the database
        PeriodTracker periodTracker = iPeriodTrackerService.getPeriodTrackerById(idPeriod);
        if (periodTracker == null) {
            return ResponseEntity.notFound().build();
        }

        // Calculate ovulation date
        LocalDate ovulationDate = iPeriodTrackerService.calculateOvulationDate(periodTracker);

        return ResponseEntity.ok(ovulationDate);
    }

    @GetMapping("/getSymptomsAndRatingsForPeriod/{idPeriod}")
    public List<SymptomRating> getSymptomsAndRatingsForPeriod(@PathVariable long idPeriod) {
        return iPeriodTrackerService.getSymptomsAndRatingsForPeriod(idPeriod);
    }

    @GetMapping("/exercises")
    public List<Exercise> getPeriodExercises() {
        return iPeriodTrackerService.getPeriodExercises();
    }

    @GetMapping("/period-food")
    public List<Food> getPeriodFood() {
        return iPeriodTrackerService.getPeriodFood();
    }


    @GetMapping("/fertile-window/{idPeriod}")
    public ResponseEntity<List<LocalDate>> getFertileWindow(@PathVariable("idPeriod") Long idPeriod) {
        PeriodTracker periodTracker = iPeriodTrackerService.getPeriodTrackerById(idPeriod);
        List<LocalDate> fertileWindow = iPeriodTrackerService.calculateFertileWindow(periodTracker);
        return new ResponseEntity<>(fertileWindow, HttpStatus.OK);
    }
}