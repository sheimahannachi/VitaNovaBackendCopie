package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Service.Sendmail;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@MultipartConfig
@RequestMapping("/sendmail/")

@CrossOrigin
public class SendmailController {

    @Autowired
    Sendmail sendmail;

    @PostMapping("{Toemail}/{Subject}/{Object}")
    public void sendmail (@PathVariable String Toemail , @PathVariable String Subject ,@PathVariable String Object  ){
         sendmail.envoyer(Toemail,Subject,Object);
    }
}
