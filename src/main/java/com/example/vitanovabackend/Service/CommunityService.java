package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService implements ICommunityService{

    CommunityRepository repository;
    @Override
    public Community addCommmunity(Community community) {
        //CREATOR A AJOUTER

        community.setCreationDate(LocalDate.now());
        return repository.save(community);
    }

    @Override
    public Community updateCommmunity(long id, Community community) {
        Community community1= repository.findById(id).orElse(null);

        if(community1==null)
            return null;

        community.setId(id);

        return community;
    }

    @Override
    public void deleteCommunity(long id) {
        repository.deleteById(id);

    }

    @Override
    public Community findCommunity(long id) {
        return repository.findById(id).orElse(null);
    }




    // à ajouter dans controller

    @Override
    public List<Community> findAllCommunity() {
        return repository.findAll();
    }

    @Override
    public List<Community> findByName(String communityName) {

        return  repository.findByCommunityName(communityName);
    }




}
