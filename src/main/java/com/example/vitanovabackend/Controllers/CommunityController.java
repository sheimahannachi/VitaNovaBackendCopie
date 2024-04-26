package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.ControllerUrls;
import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.CommunityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)

public class CommunityController {

    CommunityService service;



    @PostMapping(ControllerUrls.CommunityUrls.addCommunity)
    public ResponseEntity<Community> addCommmunity (@Valid @RequestBody Community community, @PathVariable long userId){
        return ResponseEntity.ok().body(service.addCommmunity(community,userId));

    }


    @PutMapping(ControllerUrls.CommunityUrls.UpdateCommunity)
    public ResponseEntity<Community> updateCommmunity (@PathVariable long id ,@Valid @RequestBody Community community){
        return ResponseEntity.ok().body(service.updateCommmunity(id,community));
    }

    @DeleteMapping(ControllerUrls.CommunityUrls.DeleteCommunity)
    public boolean deleteCommunity(@PathVariable long id){
        return service.deleteCommunity(id);
    }


    @GetMapping(ControllerUrls.CommunityUrls.FindCommunity)
    public Community findCommunity(@PathVariable long id){
        return service.findCommunity(id);
    }



    @GetMapping(ControllerUrls.CommunityUrls.FindAllCommunity)
    public Page<Community> findAllCommunities(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "5") int size){
        return service.findAllCommunity(page, size);
    }


    @GetMapping(ControllerUrls.CommunityUrls.FindCommunityByName)
    public Page<Community> findCommunitiesByName(@PathVariable String name
            ,@RequestParam(defaultValue = "0") int page){
        return service.findByName(name,page,PageanationSize.size);
    }



    @PutMapping(ControllerUrls.CommunityUrls.AddMemberToCommunity)
    public boolean addMember(@RequestParam long userId,@RequestParam long communityId){
        return service.addMember(userId,communityId);

    }

    @GetMapping(ControllerUrls.CommunityUrls.getCommunitiesOrderByChallenger )
    public Page<Community> getCommunitiesOrderByChallenegs(@RequestParam(defaultValue = "0" )int page){
        return service.findAllOrderByChallengesNumber(page,PageanationSize.size);
    }

    @GetMapping(ControllerUrls.CommunityUrls.fetchTopThree)
    public List<User> fetchTopThree(@RequestParam long communityId){
        return service.fetchTopThree(communityId);
    }

    @PutMapping(ControllerUrls.CommunityUrls.userLeaveCommunity)
    public boolean userLeaveCommunity(@PathVariable long userId){
        return service.userLeaveCommunity(userId);
    }

    @GetMapping(ControllerUrls.CommunityUrls.getCommunityMembers)
    public List<User> getCommunityMembers(@RequestParam long comunityId){
        return service.getCommunityMembers(comunityId);
    }

    @GetMapping(ControllerUrls.CommunityUrls.getCommunityByUser)
    public Community getCommunityByUserId(@PathVariable long userId){
        return service.getCommunityByUserId(userId);
    }

}
