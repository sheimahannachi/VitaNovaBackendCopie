package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChallengesService {


    Challenges addChallenge(Challenges challenge, long communityId);

    Challenges updateChallenge ( long id , Challenges challenge);

    void deleteChallenge(long id);

    Challenges findChallenges(long id);

    Challenges findByCommunityIdAndActive(long id);

    List<Challenges> findAllActive();



    Page<Challenges> findAll(int page, int size);



}
