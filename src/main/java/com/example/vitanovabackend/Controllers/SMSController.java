package com.example.vitanovabackend.Controllers;
import com.example.vitanovabackend.Payload.Request.EmailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RequestMapping("/api")
@RestController
public class SMSController {


    @PostMapping(value = "/sendSMS")
    public ResponseEntity<String> sendSMS(@RequestBody EmailRequest emailRequest) {
       System.out.println(System.getenv("TWILIO_ACCOUNT_SID")+"    " +System.getenv("TWILIO_AUTH_TOKEN"));
      //  Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        System.out.println("+216"+emailRequest.getTo()+" "+emailRequest.getText());
   /*     Message.creator(new PhoneNumber("+216"+emailRequest.getTo()),
             new PhoneNumber("+15034516316"), emailRequest.getText()+" 📞").create();*/

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
}
