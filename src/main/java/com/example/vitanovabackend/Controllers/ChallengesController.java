package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.Service.IChallengesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ChallengesController {
     IChallengesService service;

     @PostMapping("addChallenge")
    public Challenges addChallenge (@RequestBody Challenges challenge, @RequestParam long communityId){
        return service.addChallenge(challenge,communityId);

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

    @GetMapping("findAllActiveChallenges")
    public List<Challenges>findAllActive(){
         return service.findAllActive();
    }

    @GetMapping("findAllChallenges")
    public Page<Challenges> findAll(@RequestParam(defaultValue = "0") int page){
         return this.service.findAll(page, PageanationSize.size);
    }


}
