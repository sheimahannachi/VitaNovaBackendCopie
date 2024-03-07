package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.Service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")

public class CommunityController {

    CommunityService service;



    @PostMapping("addCommunity/{userId}")
    public Community addCommmunity (@RequestBody Community community,@PathVariable long userId){
        return service.addCommmunity(community,userId);

    }


    @PutMapping("updateComunity/{id}")
    public Community updateCommmunity (@PathVariable long id ,@RequestBody Community community){
        return service.updateCommmunity(id,community);
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
    public Page<Community> findAllCommunities(@RequestParam(defaultValue = "0") int page){
        return service.findAllCommunity(page, PageanationSize.size);
    }


    @GetMapping("/findCommunitiesByNom/{name}")
    public List<Community> findCommunitiesByName(@PathVariable String name){
        return service.findByName(name);
    }



    @PutMapping("addMemberToComunity")
    public boolean addMember(@RequestParam long userId,@RequestParam long communityId){
        return service.addMember(userId,communityId);

    }

}
