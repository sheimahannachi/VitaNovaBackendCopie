package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Repositories.ChallengeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
