package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Repositories.ChallengeRepository;
import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InactiveCommunities implements IInactiveCommunity{

    ChallengeRepository challengeRepository;
    CommunityRepository communityRepository;
    @Override
    public boolean inactiveCommunities() {
        boolean retour=false;
        List<Community>communities=communityRepository.findAllByStatusIsTrue();
        if(communities.isEmpty()){
            return false;
        }
        for(Community community:communities){
            Challenges challenge=challengeRepository.findLatestByCommunity(community.getId()).orElse(null);
            if(challenge!=null ) {
                if (challenge.getCreationDate().isBefore(LocalDate.now().minusMonths(1))) {

                    retour = true;
                    community.setStatus(false);
                    communityRepository.save(community);
                    log.info(community.getCommunityName() + " is not active anymore");


                }
            }
        }


        return retour;
    }

}

