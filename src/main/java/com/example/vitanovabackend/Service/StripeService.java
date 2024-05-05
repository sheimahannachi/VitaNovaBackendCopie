package com.example.vitanovabackend.Service;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class StripeService implements  StripeIService{

    private final String stripeApiKey ="sk_test_51P6CjQRozZ5IzKXIPsbKKfqSHLiyUbr98MgfQpqwxoiULglTxHru0QyeYKArfHE2bvYgSRMKZUnsDRpNisbHymdi00V6duA7cE";

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public void createCharge( double amount, String currency) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (amount * 100)); // Amount in cents
        params.put("currency", currency);
        params.put("source", "tok_visa"); // Token generated from client-side Stripe.js or Checkout
        Charge charge = Charge.create(params);
        // Handle successful charge
    }
}
