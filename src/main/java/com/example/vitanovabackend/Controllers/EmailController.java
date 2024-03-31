package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Payload.Request.EmailRequest;
import com.example.vitanovabackend.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;


@RequestMapping("/api")
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
    }



    @PostMapping("/sendEmailWithAttachment")
    public ResponseEntity<String> sendEmailWithAttachment(@RequestBody EmailRequest request, HttpServletRequest httpRequest) {
        String to = request.getTo();
        String subject = request.getSubject();
        String text = request.getText();
        String ipAddress = emailService.getWANIPAddress();
        String location = emailService.getLocationFromIPAddress(ipAddress);

        String attachmentPath = request.getAttachmentPath();
        if(attachmentPath==""){attachmentPath="C:\\Users\\hamad\\OneDrive\\Desktop\\test.jpg";}
        try {
            text += "\nConnection from IP address: " + ipAddress+ " Location : "+location;

            emailService.sendEmailWithAttachment(to, subject, text, attachmentPath);
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to read attachment file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send email with attachment", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    public static String encodeImageToBase64(String imagePath) throws IOException {
        // Read the image file into a byte array
        byte[] imageData = Files.readAllBytes(Path.of(imagePath));

        // Encode the image data into base64 format
        return Base64.getEncoder().encodeToString(imageData);
    }

}
