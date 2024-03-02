package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;

import java.util.List;

public interface ICommunityService {

    Community addCommmunity (Community community, long userId);

    Community updateCommmunity ( long id , Community community);

    void deleteCommunity(long id);

    Community findCommunity(long id);

    List<Community> findAllCommunity();

    List<Community> findByName(String CommunityName);

    boolean addMember(long userId,long communityId);
}
