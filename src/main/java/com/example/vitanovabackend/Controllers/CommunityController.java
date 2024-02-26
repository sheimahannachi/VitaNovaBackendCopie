package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.Service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommunityController {

    CommunityService service;

    @PostMapping("addCommunity")
    public Community addCommmunity (@RequestBody Community community){
        return service.addCommmunity(community);

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
    public List<Community> findAllCommunities(){
        return service.findAllCommunity();
    }

    @GetMapping("/findCommunitiesByNom/{name}")
    public List<Community> findCommunitiesByName(@PathVariable String name){
        return service.findByName(name);
    }



}
