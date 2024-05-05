package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICommunityService {

    Community addCommmunity (Community community, long userId);

    Community updateCommmunity ( long id , Community community);

    boolean deleteCommunity(long id);

    Community findCommunity(long id);

    Page<Community> findAllCommunity(int page, int size);

    Page<Community> findByName(String CommunityName, int page , int size);

    boolean addMember(long userId,long communityId);

    Page<Community> findAllOrderByChallengesNumber(int page , int size);

    List<User>fetchTopThree(long communityId);

    boolean userLeaveCommunity(long userId);

    List<User> getCommunityMembers(long comunityId);

    Community getCommunityByUserId(long userId);


}
