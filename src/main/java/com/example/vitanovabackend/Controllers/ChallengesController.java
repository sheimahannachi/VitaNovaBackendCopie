package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.ControllerUrls;
import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.Service.IChallengesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ChallengesController {
     IChallengesService service;

     @PostMapping(ControllerUrls.ChallengesUrl.AddChallenge)
    public ResponseEntity<Challenges> addChallenge (@Valid @RequestBody Challenges challenge, @RequestParam long communityId){
        return ResponseEntity.ok(service.addChallenge(challenge,communityId));

    }


    @PutMapping(ControllerUrls.ChallengesUrl.UpdateChallenge)
    public ResponseEntity<Challenges> updateChallenge (@PathVariable long id ,@Valid @RequestBody Challenges challenge){
         return ResponseEntity.ok( service.updateChallenge(id,challenge));
    }


    @DeleteMapping(ControllerUrls.ChallengesUrl.DeleteChallenge)
    public void deleteChallenge(@PathVariable long id){
         service.deleteChallenge(id);
    }

    @GetMapping(ControllerUrls.ChallengesUrl.FindChallenge)
    public Challenges findChallenges(@PathVariable long id){
         return service.findChallenges(id);
    }


    @GetMapping(ControllerUrls.ChallengesUrl.FindActiveChallengeByCommunity)
    public Challenges findActiveByCommunity(@PathVariable long id){
         return service.findByCommunityIdAndActive(id);

    }

    @GetMapping(ControllerUrls.ChallengesUrl.FindAllActiveChallenge)
    public List<Challenges>findAllActive(){
         return service.findAllActive();
    }

    @GetMapping(ControllerUrls.ChallengesUrl.FindAll)
    public Page<Challenges> findAll(@RequestParam(defaultValue = "0") int page){
         return this.service.findAll(page, PageanationSize.size);
    }


}
