package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.CommunicationRepository;

import com.example.vitanovabackend.DAO.Repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunicationService implements  ICommunicationService{

    CommunicationRepository repository;
    CommunityRepository communityRepository;

    @Override
    public Communication addCommunication(Communication communication) {
        Communication communicationRecieved;

        if(communication.getCommunity()!=null){
             communicationRecieved = Communication.builder()
                    .message(communication.getMessage())
                    .seen(false)
                    .sentDate(LocalDate.now())
                    .sender(communication.getSender())
                    .community(communication.getCommunity())
                    .build();
            return repository.save(communicationRecieved) ;
        }
        if(communication.getReciever()!=null) {
             communicationRecieved = Communication.builder()
                    .message(communication.getMessage())
                    .seen(false)
                    .sentDate(LocalDate.now())
                    .sender(communication.getSender())
                    .reciever(communication.getReciever())
                    .build();
            return repository.save(communicationRecieved) ;
        }else return null;



    }

    @Override
    public Communication updateCommunication(long id, Communication communication) {
        Communication communication1 = repository.findById(id).orElse(null);
        if(communication1==null)
            return null;
        communication.setId(id);
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
    public List<Communication> findBySenderAndReciever(User sender, User reciever) {
        return repository.findBySenderAndReciever(sender,reciever);
    }

    @Override
    public Page<Communication> findByCommunity(long communityId, int page, int size) {
        Community community=communityRepository.findById(communityId).orElse(null);

        if(community==null){
            return null;
        }

        Pageable pageable= PageRequest.of(page,size);
        return repository.findAllByCommunity(community,pageable);

    }
}
