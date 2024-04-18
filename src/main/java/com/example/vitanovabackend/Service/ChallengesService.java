package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Repositories.ChallengeRepository;
import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengesService implements IChallengesService {

    ChallengeRepository repository;
    CommunityRepository communityRepository;
    @Override
    public Challenges addChallenge(Challenges challenge,long communityId) {
        Community community=communityRepository.findById(communityId).orElse(null);

        if(community==null)
            return null;

        challenge.setActive(true);
        challenge.setCreationDate(LocalDate.now());
        if(!community.isStatus()){
            community.setStatus(true);
        }
        communityRepository.save(community);
        challenge.setCommunity(community);




        return repository.save(challenge);
    }

    @Override
    public Challenges updateChallenge(long id, Challenges challenge) {
        Challenges challenge1= repository.findById(id).orElse(null);
        if(challenge1==null)
            return null;

        challenge.setId(id);
        challenge.setActive(true);
        challenge.setCreationDate(LocalDate.now());
        challenge.setCommunity(challenge1.getCommunity());

        return repository.save(challenge);
    }

    @Override
    public void deleteChallenge(long id) {
        repository.deleteById(id);

    }

    @Override
    public Challenges findChallenges(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Challenges findByCommunityIdAndActive(long id) {
        return repository.findByActiveTrueAndCommunityId(id);
    }

    @Override
    public List<Challenges> findAllActive() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Page<Challenges> findAll(int page,int size) {
        Pageable pageable= PageRequest.of(page,size);
        return repository.findAll(pageable);
    }


}
