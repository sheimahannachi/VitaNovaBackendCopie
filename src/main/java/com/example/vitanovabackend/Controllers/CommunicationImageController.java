package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Service.CommunicationImageService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor

public class CommunicationImageController {
    CommunicationImageService communicationImageService;
    @PostMapping("UploadImage")

    public ResponseEntity<String> UploadImage(@RequestParam("image") MultipartFile image){

        //return communicationImageService.addImage(image);
        String imagePath = communicationImageService.addImage(image);
        if (imagePath != null) {
            return ResponseEntity.ok().body(imagePath);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
