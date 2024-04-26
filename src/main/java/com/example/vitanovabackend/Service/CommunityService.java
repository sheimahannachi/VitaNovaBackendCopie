package com.example.vitanovabackend.Service;

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
        community.setCreator(creator);
        Community communitySaved =repository.save(community);
        creator.setCommunity(communitySaved);
        userRepository.save(creator);
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
    public boolean deleteCommunity(long id) {
        Community community=repository.findById(id).orElse(null);
        if(community!=null){
        List<Communication>communications=communicationRepository.findByCommunity(community);
        communicationRepository.deleteAll(communications);

        for(User member:community.getMembres()){
            member=userRepository.findById(member.getIdUser()).orElse(null);
            if(member!=null){
                member.setCommunity(null);
                userRepository.save(member);
            }

        }




        repository.deleteById(id);
        return  true;
        }
        return false;

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

    @Override
    public List<User> fetchTopThree(long communityId) {
        return repository.topThree(communityId,PageRequest.of(0,3));
    }

    @Override
    public boolean userLeaveCommunity(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user==null)
            return false;


        user.setCommunity(null);
        userRepository.save(user);

        return true;
    }

    @Override
    public List<User> getCommunityMembers(long comunityId) {
        return repository.getCommunityMembers(comunityId);
    }

    @Override
    public Community getCommunityByUserId(long userId) {
        return repository.getCommunityByUserId(userId).orElse(null);
    }


}
