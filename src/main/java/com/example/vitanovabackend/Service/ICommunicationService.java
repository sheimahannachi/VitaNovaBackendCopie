package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Communication;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface ICommunicationService {

    Communication addCommunication (Communication communication);

    Communication updateCommunication ( long id , Communication communication);

    void deleteCommunication(long id);

    Communication findCommunication(long id);

    List<Communication> findallCommunications();

    Page<Communication> findBySenderAndReciever(long sender, long reciever, int page, int size);
    Page<Communication> findByCommunity(long communityId, int page, int size);

    boolean setSeenToComunicationComunity(long comunityId, long senderId);
    boolean setSeenToComunicationOneToOne(long senderId, long recieverId);

    Communication addAudioCommunication(Communication audioData);

}