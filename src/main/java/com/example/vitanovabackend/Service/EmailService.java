package com.example.vitanovabackend.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        System.out.println("to : " + to);
        System.out.println("subject : " + subject);
        System.out.println("text : " + text);

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


    public String getWANIPAddress() {
        try {
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ipAddress = in.readLine();
            in.close();
            return ipAddress;
        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to retrieve WAN IP address";
        }

    }


    public String getLocationFromIPAddress(String ipAddress) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ipAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            String country = jsonObject.getString("country");
            String region = jsonObject.getString("regionName");
            String city = jsonObject.getString("city");
            return country + ", " + region + ", " + city;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Unable to retrieve location information";
        }


    }
}
