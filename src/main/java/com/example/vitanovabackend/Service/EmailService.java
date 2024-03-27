package com.example.vitanovabackend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        System.out.println("to : "+ to);
        System.out.println("subject : "+ subject);
        System.out.println("text : "+ text);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String text, String picturePath) throws Exception {
        System.out.println("to : " + to);
        System.out.println("subject : " + subject);
        System.out.println("text : " + text);
        System.out.println("picturePath : " + picturePath);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        File pictureFile = new File(picturePath);
        String pictureName = MimeUtility.encodeWord(pictureFile.getName(), "utf-8", "B");
        helper.addAttachment(pictureName, pictureFile);

        emailSender.send(message);
    }

}
