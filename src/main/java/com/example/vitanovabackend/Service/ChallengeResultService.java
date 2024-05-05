package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ChallengeResultService implements IChallengeResultService {

    ChallengeRepository repository;
    CommunityRepository communityRepository;
    TrackerRepository trackerRepository;
    HydrationRepository hydrationRepository;
    UserRepository userRepository;
    PersonalGoalsRepository personalGoalsRepository;
    @Override
    public void ChallengeResult() {
        List<Challenges> challenges = repository.findAllByActiveTrue();

        if (!challenges.isEmpty()) {

            /////// all Challengesthis Week
            for (Challenges challenge : challenges) {
                Community community = communityRepository.findById(challenge.getCommunity().getId()).orElse(null);

                log.info(challenge.getName());
                if (community != null) {
                    log.info("Acitivities for: " + community.getCommunityName());

                    List<User> members = repository.communityMembers(community.getId());
                    if (!members.isEmpty()) {


                        for (User user : members) {
                            log.info(user.getFirstName());

                            if (challenge.getType().equals(ChallengeType.CALORIES)) {


                                long caloriesConsumed = sumCaloriesWeek(user);
                                if (challenge.getCompare().equals(ChallengeCompare.LESS)) {

                                    if (challenge.getGoal() > caloriesConsumed || challenge.getGoal() == caloriesConsumed) {
                                        long communityActivity = user.getComunityActivity();
                                        user.setComunityActivity(communityActivity + 1);
                                        log.info("+1 for" + user.getFirstName() + " " + user.getLastName());
                                        userRepository.save(user);
                                    }
                                } else {
                                    if (challenge.getGoal() < caloriesConsumed || challenge.getGoal() == caloriesConsumed) {
                                        long communityActivity = user.getComunityActivity();
                                        user.setComunityActivity(communityActivity + 1);
                                        log.info("+1 for" + user.getFirstName() + " " + user.getLastName());
                                        userRepository.save(user);
                                    }


                                }
                            }

                            if (challenge.getType().equals(ChallengeType.WATER)) {

                                long waterConsumed = sumWaterWeek(user);


                                if (challenge.getGoal() < waterConsumed || challenge.getGoal() == waterConsumed) {

                                    long communityActivity = user.getComunityActivity();
                                    user.setComunityActivity(communityActivity + 1);
                                    log.info("user com act" + user.getComunityActivity());
                                    userRepository.save(user);

                                }

                            }


                        }
                    }
                }
            }
        }
    }






    public long sumCaloriesWeek(User user){
        LocalDate today= LocalDate.now();
        long som=0;

        User user1=userRepository.findById(user.getIdUser()).orElse(null);
        if(user1==null){
            log.info("No User found while calculationg calories consumption");
            return 0;
        }
        PersonalGoals personalGoals=personalGoalsRepository.findById(user1.getPersonalGoals().getIdPG()).orElse(null);

        if(personalGoals==null){
            log.info("No personalGoals found while calculationg calories consumption");
            return 0;
        }
        List<Tracker> trackers= trackerRepository.findTrackerByDateBetweenAndPersonalGoals(today.minusWeeks(1),today,personalGoals);

        if(trackers.isEmpty()){
            log.info("tracker is empty");
            return 0 ;
        }

        for(Tracker tracker:trackers){
            som=som+(long)tracker.getConsumedcalories();
        }

        return som;
    }


    public long sumWaterWeek(User user){
        long sum=0;
        LocalDate today = LocalDate.now();

        User user1=userRepository.findById(user.getIdUser()).orElse(null);
        if(user1==null){
            log.info("No User found while calculationg water consumption");

            return 0;
        }
        log.info("while calculating water "+user1.getFirstName());


        //////////////////////////////////////////////////////// probably will be changer


        PersonalGoals personalGoals=personalGoalsRepository.findById(user1.getPersonalGoals().getIdPG()).orElse(null);

        if(personalGoals==null)
            return 0;
        List<Tracker> trackers= trackerRepository.findTrackerByDateBetweenAndPersonalGoals(today.minusWeeks(1),today,personalGoals);

        if(!trackers.isEmpty()) {
            log.info("tracker is not empty");
            for (Tracker tracker : trackers) {
                Hydration hydration = hydrationRepository.findById(tracker.getHydration().getId()).orElse(null);
                if(hydration!=null){

                    sum=sum+(long)hydration.getSumofwater();
                }
                else
                    log.info("No hydration found while calculating water consumption");

            }
        }else log.info("No tracker found while calculating water consumption");




        return sum;
    }
















}

