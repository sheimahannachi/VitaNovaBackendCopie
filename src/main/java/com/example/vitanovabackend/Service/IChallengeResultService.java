package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.User;

public interface IChallengeResultService {

    void ChallengeResult();
    long sumCaloriesWeek(User user);
    long sumWaterWeek(User user);
}

