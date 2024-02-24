package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.Service.IChallengesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ChallengesController {
     IChallengesService service;

     @PostMapping("addChallenge")
    public Challenges addChallenge (@RequestBody Challenges challenge){
        return service.addChallenge(challenge);

    }


    @PutMapping("updateChallenge")
    public Challenges updateChallenge (@RequestParam long id ,@RequestBody Challenges challenge){
         return service.updateChallenge(id,challenge);
    }


    @DeleteMapping("deleteChallenge")
    public void deleteChallenge(@RequestParam long id){
         service.deleteChallenge(id);
    }

    @GetMapping("findChallenge")
    public Challenges findChallenges(@RequestParam long id){
         return service.findChallenges(id);
    }




}
