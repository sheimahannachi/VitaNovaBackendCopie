package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Communication;

import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.Service.ICommunicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommunicationController {

    ICommunicationService service;


    @PostMapping("addCommunication")
    public Communication addCommunication (@RequestBody  Communication communication){
        return service.addCommunication(communication);
    }

    @PutMapping("updateCommunication/{id}")
    public Communication updateCommunication (@PathVariable long id ,@RequestBody Communication communication){
        return service.updateCommunication(id,communication);
    }

    @DeleteMapping("deleteCommunication/{id}")
    public void deleteCommunication(@PathVariable long id){
        service.deleteCommunication(id);
    }

    @GetMapping("findCommunication/{id}")
    public Communication findCommunication(@PathVariable long id){
        return service.findCommunication(id);
    }

    @GetMapping("findAllCommunications")
    public List<Communication> findAllComunications(){
        return service.findallCommunications();
    }

    @GetMapping("findBySenderAndReciever")
    public List<Communication> findBySenderAndReciever(@RequestBody User sender,@RequestBody User reciever){
        return service.findBySenderAndReciever(sender,reciever);
    }



}
