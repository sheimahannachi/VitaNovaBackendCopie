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
        public static final String fetchTopThree="getTopThreeByCommunity";
        public static final String userLeaveCommunity="leaveCommunity/{userId}";
        public static final String getCommunityMembers="communityMembers";
        public static final String getCommunityByUser="communityByUser/{userId}";

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
        public final static String getCommunicationByCommunity="getCommbyCommunityFirst";

        public final static String deleteComunicationUrl="deleteCommunication/{id}";
        public final static String updateCommunication="updateCommunication/{id}";
        public final static String seenComunication="seenComunications";
        public final static String getComunicationbySenderAndReciever="findBySenderAndReciever";
        public final static String setSeenToCommunicationsOneToOne="setSeenToComOneToOne";
    }


    public static class UserUrls {
        public final static String AuthLoginUrl="/api/generateToken";
        public final static String AuthSignupUrl="/api/signup";
        public final static String MiscTakePic="api/misc/takePic";
        public final static String AuthResetPasswordE="api/misc/takePic";
        public final static String AuthResetPasswordP="api/misc/takePic";

    }





}
