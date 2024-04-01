package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.CommunicationRepository;
import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService implements ICommunityService{

    CommunityRepository repository;
    UserRepository userRepository;
    CommunicationRepository communicationRepository;
    @Override
    public Community addCommmunity(Community community, long userId) {
        //CREATOR A AJOUTER
        User creator=userRepository.findById(userId).orElse(null);

        if(creator==null)
            return null;

        community.setStatus(true);
        community.setCreationDate(LocalDate.now());
        Community communitySaved =repository.save(community);
        creator.setCommunity(communitySaved);
        return communitySaved;
    }

    @Override
    public Community updateCommmunity(long id, Community community) {
        Community community1= repository.findById(id).orElse(null);

        if(community1==null)
            return null;

        community.setId(id);
        repository.save(community);

        return community;
    }

    @Override
    public void deleteCommunity(long id) {
        Community community=repository.findById(id).orElse(null);
        if(community!=null){
        List<Communication>communications=communicationRepository.findByCommunity(community);
        communicationRepository.deleteAll(communications);
        repository.deleteById(id);
        }

    }

    @Override
    public Community findCommunity(long id) {
        return repository.findById(id).orElse(null);
    }




    // à ajouter dans controller

    @Override
    public Page<Community> findAllCommunity(int page , int size) {
        Pageable pageable= PageRequest.of(page,size);
        return repository.findAll(pageable);
    }

    @Override
    public Page<Community> findByName(String communityName,int page,int size) {
        Pageable pageable= PageRequest.of(page,size);

        return  repository.findByCommunityName(communityName,pageable);
    }

    @Override
    public boolean addMember(long userId, long communityId) {
        User member= userRepository.findById(userId).orElse(null);
        Community community=repository.findById(communityId).orElse(null);

        if(member==null || community==null )
            return false;

        member.setCommunity(community);
        member.setComunityActivity(0);

        userRepository.save(member);






        return true;
    }

    @Override
    public Page<Community> findAllOrderByChallengesNumber(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        return repository.findAllOrderByCountChallenges(pageable);
    }




}
