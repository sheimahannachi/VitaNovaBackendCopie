package com.example.vitanovabackend.Service;

import java.io.File;
import java.util.Base64;
import com.example.vitanovabackend.Configuration.EncryptionUtils;
import com.example.vitanovabackend.Configuration.FileUtils;
import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Repositories.CommunicationRepository;

import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunicationService implements  ICommunicationService{

    CommunicationRepository repository;
    CommunityRepository communityRepository;
    FileUtils fileUtils;



    @Override
    public Communication addCommunication(Communication communication) {
        Communication communicationRecieved;



        try {
            String encryptedMessage = EncryptionUtils.encrypt(communication.getMessage());




                if (communication.getCommunity() != null) {
                    communicationRecieved = Communication.builder()
                            .message(encryptedMessage)
                            .seen(false)
                            .sentDate(LocalDate.now())
                            .sender(communication.getSender())
                            .community(communication.getCommunity())
                            .imageSent(communication.getImageSent())
                            .build();
                    communicationRecieved = repository.save(communicationRecieved);

                    //Method Return
                    communicationRecieved.setMessage(
                            EncryptionUtils.decrypt(communicationRecieved.getMessage())
                    );
                    return communicationRecieved;
                }
                if (communication.getReciever() != null) {
                    communicationRecieved = Communication.builder()
                            .message(encryptedMessage)
                            .seen(false)
                            .sentDate(LocalDate.now())
                            .sender(communication.getSender())
                            .reciever(communication.getReciever())
                            .imageSent(communication.getImageSent())
                            .build();
                    communicationRecieved = repository.save(communicationRecieved);

                    //Method Return
                    communicationRecieved.setMessage(
                            EncryptionUtils.decrypt(communicationRecieved.getMessage())
                    );
                    return communicationRecieved;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;


        }


    @Override
    public Communication updateCommunication(long id, Communication communication) {
        Communication communication1 = repository.findById(id).orElse(null);
        if(communication1==null)
            return null;

        communication.setId(id);
        communication.setMessage(
                EncryptionUtils.encrypt(communication.getMessage())
        );
        communication.setSeen(false);

        return repository.save(communication);
    }

    @Override
    public void deleteCommunication(long id) {

        repository.deleteById(id);

    }

    @Override
    public Communication findCommunication(long id) {

        Communication communication= repository.findById(id).orElse(null);
        if(communication!=null && !communication.isSeen()) {
            communication.setSeen(true);
        }

        return communication;
    }

    @Override
    public List<Communication> findallCommunications() {
        return repository.findAll();
    }

    @Override
    public Page<Communication> findBySenderAndReciever(long sender, long reciever, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Communication> retour=repository.findBySenderAndOrReciever(sender,reciever,pageable);
        for(Communication communication:retour){
            communication.setMessage(
                    EncryptionUtils.decrypt(communication.getMessage())
            );
        }
        return retour;

    }

    @Override
    public Page<Communication> findByCommunity(long communityId, int page, int size) {
        Community community=communityRepository.findById(communityId).orElse(null);

        if(community==null){
            return null;
        }


        Pageable pageable = PageRequest.of(page, size);
         Page<Communication> p =repository.findAllByCommunityOrderByIdDesc(community,pageable);
         try {
             for (Communication communication : p) {
                 communication.setMessage(
                         EncryptionUtils.decrypt(communication.getMessage())
                 );

             }
         } catch (Exception e) {
             e.printStackTrace();
         }

         return p;
    }

    @Override
    public boolean setSeenToComunicationComunity(long comunityId, long senderId) {
        Community community=communityRepository.findById(comunityId).orElse(null);
        if(community==null)
            return false;
        List<Communication> coms=new ArrayList<>();
        for(Communication c:repository.getCommunicationByCommunityIdAndSeenIsFalse(comunityId)){
            if(c.getSender().getIdUser()!=senderId) {
                c.setSeen(true);
                coms.add(c);
            }
        }
        repository.saveAll(coms);
        return true;
    }

    @Override
    public boolean setSeenToComunicationOneToOne(long senderId, long recieverId) {
        List<Communication> listFound=repository.getCommunicationBySenderIdUserAndRecieverIdUserAndSeenIsFalse(senderId,recieverId);
        if(listFound==null||listFound.isEmpty()){
            return false;
        }
        for(Communication c : listFound){
            c.setSeen(true);
            repository.save(c);

        }
        return true;
    }

    @Override
    public Communication addAudioCommunication(Communication audioData) {
        audioData.setMessage("Audio Message");

        return null;
    }


}
