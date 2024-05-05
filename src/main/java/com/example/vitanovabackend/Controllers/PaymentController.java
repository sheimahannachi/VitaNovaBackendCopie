package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.PaymentRequest;
import com.example.vitanovabackend.Service.StripeIService;
import com.example.vitanovabackend.Service.StripeService;
import com.stripe.exception.StripeException;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@MultipartConfig
@RequestMapping("/Payment")
public class PaymentController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/charge")
    public String chargeCard(@RequestBody PaymentRequest paymentRequest) {
        return stripeService.processPayment(paymentRequest);
    }


}
