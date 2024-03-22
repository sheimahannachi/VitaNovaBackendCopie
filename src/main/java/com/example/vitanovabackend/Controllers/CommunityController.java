package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.Service.CommunityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

public class CommunityController {

    CommunityService service;



    @PostMapping("addCommunity/{userId}")
    public ResponseEntity<Community> addCommmunity (@Valid @RequestBody Community community, @PathVariable long userId){
        return ResponseEntity.ok().body(service.addCommmunity(community,userId));

    }


    @PutMapping("updateComunity/{id}")
    public ResponseEntity<Community> updateCommmunity (@PathVariable long id ,@Valid @RequestBody Community community){
        return ResponseEntity.ok().body(service.updateCommmunity(id,community));
    }

    @DeleteMapping("deleteCommunity/{id}")
    public void deleteCommunity(@PathVariable long id){
        service.deleteCommunity(id);
    }


    @GetMapping("findCommunity/{id}")
    public Community findCommunity(@PathVariable long id){
        return service.findCommunity(id);
    }



    @GetMapping("/findAllCommunities")
    public Page<Community> findAllCommunities(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "5") int size){
        return service.findAllCommunity(page, size);
    }


    @GetMapping("/findCommunitiesByNom/{name}")
    public Page<Community> findCommunitiesByName(@PathVariable String name
            ,@RequestParam(defaultValue = "0") int page){
        return service.findByName(name,page,PageanationSize.size);
    }



    @PutMapping("addMemberToComunity")
    public boolean addMember(@RequestParam long userId,@RequestParam long communityId){
        return service.addMember(userId,communityId);

    }

    @GetMapping("getCommunitiesOrderedByChallenges")
    public Page<Community> getCommunitiesOrderByChallenegs(@RequestParam(defaultValue = "0" )int page){
        return service.findAllOrderByChallengesNumber(page,PageanationSize.size);
    }

}
