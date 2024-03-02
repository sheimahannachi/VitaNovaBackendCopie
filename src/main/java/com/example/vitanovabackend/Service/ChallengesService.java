package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Repositories.ChallengeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ChallengesService implements IChallengesService {

    ChallengeRepository repository;
    @Override
    public Challenges addChallenge(Challenges challenge) {
        challenge.setActive(true);
        challenge.setCreationDate(LocalDate.now());
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


}
