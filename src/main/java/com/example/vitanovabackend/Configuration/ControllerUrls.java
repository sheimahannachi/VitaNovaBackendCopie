package com.example.vitanovabackend.Configuration;

public   class ControllerUrls {

    public static class CommunityUrls {
        public static final String  addCommunity= "addCommunity/{userId}";
        public static final String UpdateCommunity="updateComunity/{id}";
        public static final String DeleteCommunity="deleteCommunity/{id}";
        public static final String FindCommunity="findCommunity/{id}";
        public static final String FindAllCommunity="findAllCommunities";
        public static final String FindCommunityByName="/findCommunitiesByNom/{name}";
        public static final String AddMemberToCommunity="addMemberToComunity";
        public static final String getCommunitiesOrderByChallenger="getCommunitiesOrderedByChallenges";

    }
    public static class ChallengesUrl{

        public static final String AddChallenge="addChallenge";
        public static final String UpdateChallenge="updateChallenge/{id}";
        public static final String DeleteChallenge="deleteChallenge/{id}";
        public static final String FindChallenge="findChallenge/{id}";
        public static final String FindAllActiveChallenge="findAllActiveChallenges";
        public static final String FindAll="findAllChallenges";
        public static final String FindActiveChallengeByCommunity="findActiveChallengeByCommunity/{id}";

    }
    public static class chatUrl{



    }

    public static class CommunicationUrl{
        public final static String getCommunicationByCommunity="getCommbyCommunity";
    }





}
