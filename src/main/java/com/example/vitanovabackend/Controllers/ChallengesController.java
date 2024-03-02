package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.Service.IChallengesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChallengesController {
     IChallengesService service;

     @PostMapping("addChallenge")
    public Challenges addChallenge (@RequestBody Challenges challenge){
        return service.addChallenge(challenge);

    }


    @PutMapping("updateChallenge/{id}")
    public Challenges updateChallenge (@PathVariable long id ,@RequestBody Challenges challenge){
         return service.updateChallenge(id,challenge);
    }


    @DeleteMapping("deleteChallenge/{id}")
    public void deleteChallenge(@PathVariable long id){
         service.deleteChallenge(id);
    }

    @GetMapping("findChallenge/{id}")
    public Challenges findChallenges(@PathVariable long id){
         return service.findChallenges(id);
    }


    @GetMapping("findActiveChallengeByCommunity/{id}")
    public Challenges findActiveByCommunity(@PathVariable long id){
         return service.findByCommunityIdAndActive(id);

    }

    @GetMapping("findAllActive")
    public List<Challenges>findAllActive(){
         return service.findAllActive();
    }


}
