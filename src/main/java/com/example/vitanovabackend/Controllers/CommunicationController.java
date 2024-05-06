package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Configuration.ControllerUrls;
import com.example.vitanovabackend.Configuration.PageanationSize;
import com.example.vitanovabackend.DAO.Entities.Communication;

import com.example.vitanovabackend.Service.ICommunicationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class CommunicationController {

    ICommunicationService service;


 /*   @PostMapping("addCommunication")
    public Communication addCommunication (@RequestBody  Communication communication){
        return service.addCommunication(communication);
    }*/

    @PutMapping(ControllerUrls.CommunicationUrl.updateCommunication)
    public Communication updateCommunication (@PathVariable long id ,@RequestBody Communication communication){
        return service.updateCommunication(id,communication);
    }

    @DeleteMapping(ControllerUrls.CommunicationUrl.deleteComunicationUrl)
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

    @GetMapping(ControllerUrls.CommunicationUrl.getComunicationbySenderAndReciever)
    public Page<Communication> findBySenderAndReciever(@RequestParam long sender,@RequestParam long reciever,@RequestParam(defaultValue = "0") int page){
        return service.findBySenderAndReciever(sender,reciever,page,PageanationSize.size);
    }

    @GetMapping(ControllerUrls.CommunicationUrl.getCommunicationByCommunity)
    public Page<Communication> getCommunicationByCommunity(@RequestParam long communityId,@RequestParam(defaultValue = "0")int page)
    {
        return service.findByCommunity(communityId,page, PageanationSize.size);
    }


    @PutMapping(ControllerUrls.CommunicationUrl.seenComunication)
    public boolean setSeenToComunication(@RequestParam long comunityId,@RequestParam long senderId){
        log.info(""+comunityId);
        return service.setSeenToComunicationComunity(comunityId,senderId);
    }

    @PutMapping(ControllerUrls.CommunicationUrl.setSeenToCommunicationsOneToOne)
    public boolean setSeenToComunicationOneToOne(@RequestParam long senderId,@RequestParam long recieverId){
        return service.setSeenToComunicationOneToOne(senderId,recieverId);
    }



}
