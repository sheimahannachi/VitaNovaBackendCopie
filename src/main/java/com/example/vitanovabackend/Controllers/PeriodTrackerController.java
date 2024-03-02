package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.PeriodTracker;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.IPeriodTrackerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
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
  @PutMapping("archivePeriod")
    public String archivePeriod(@RequestParam Long idPeriod){
        return iPeriodTrackerService.archivePeriod(idPeriod);
  }
  @GetMapping("ArchivedPeriods")
    public List<PeriodTracker>searchArchivedPeriodsForUser(@RequestParam long idUser){
      return iPeriodTrackerService.searchArchivedPeriodsForUser(idUser);
  }
}

