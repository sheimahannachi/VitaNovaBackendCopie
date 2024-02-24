package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Community;

import java.util.List;

public interface ICommunityService {

    Community addCommmunity (Community community);

    Community updateCommmunity ( long id , Community community);

    void deleteCommunity(long id);

    Community findCommunity(long id);

    List<Community> findAllCommunity();

    List<Community> findByName(String CommunityName);
}
