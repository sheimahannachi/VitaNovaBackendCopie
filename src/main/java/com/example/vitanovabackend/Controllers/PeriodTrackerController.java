package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.IPeriodTrackerService;
import lombok.AllArgsConstructor;
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
    @PostMapping("/calculate-next-period-date")
    public ResponseEntity<String> calculateNextPeriodDate(@RequestBody PeriodTracker periodTracker) {
        LocalDate nextPeriodDate = iPeriodTrackerService.calculateNextPeriodDate(periodTracker);
        return ResponseEntity.ok("Next period date: " + nextPeriodDate.toString());
    }
    @PostMapping("/calculate-ovulation-date")
    public ResponseEntity<String> calculateOvulationDate(@RequestBody PeriodTracker periodTracker) {
        LocalDate ovulationDate = iPeriodTrackerService.calculateOvulationDate(periodTracker);
        return ResponseEntity.ok("Ovulation date: " + ovulationDate.toString());
    }

}
