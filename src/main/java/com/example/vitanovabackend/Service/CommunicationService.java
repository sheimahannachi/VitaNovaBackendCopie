package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.CommunicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunicationService implements  ICommunicationService{

    CommunicationRepository repository;

    @Override
    public Communication addCommunication(Communication communication) {

        // Sender and reciever are to be added either comunity or one to one
        communication.setSentDate(LocalDate.now());
        return repository.save(communication) ;
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
}
