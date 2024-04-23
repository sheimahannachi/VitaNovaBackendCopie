package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.Service.StripeIService;
import com.example.vitanovabackend.Service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private StripeIService stripeIService;

    @CrossOrigin("*")
    @PostMapping("/charge")
    public void charge( @RequestParam("amount") double amount, @RequestParam("currency") String currency) throws StripeException {
        stripeIService.createCharge(amount, currency);
    }
}
