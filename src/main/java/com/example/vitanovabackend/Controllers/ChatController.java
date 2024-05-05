package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Communication;

import com.example.vitanovabackend.Service.ICommunicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@Slf4j
@AllArgsConstructor
public class ChatController {

    private ICommunicationService service;

    @MessageMapping("/chat.sendMessage/{t}")
    @SendTo("/topic/{t}")
    public Communication sendMessage(
            @Payload Communication chatMessage

            ,@DestinationVariable String t
    ) {

        log.info(t);
        log.info(chatMessage.getMessage());
        log.info(chatMessage.getSender().getFirstName());
        log.info(chatMessage.getImageSent());
        //log.info(chatMessage.getCommunity().getId()+"");



        chatMessage=service.addCommunication(chatMessage);
        return chatMessage;
    }



    @MessageMapping("/chat.addUser/{t}")
    @SendTo("/topic/{t}")
    public Communication addUser(
            @Payload Communication chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            @DestinationVariable String t
    ) {
        log.info(chatMessage.getSender().getFirstName());
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender().getFirstName());
        return chatMessage;
    }
}
