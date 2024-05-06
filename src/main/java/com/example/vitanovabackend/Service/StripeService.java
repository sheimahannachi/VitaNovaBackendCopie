package com.example.vitanovabackend.Service;


import com.example.vitanovabackend.DAO.Entities.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;


@Service
@AllArgsConstructor
public class StripeService implements  StripeIService{



    private static final String SECRET_KEY = "sk_test_51P9Bv8Rw4zyZg47XvCsQ3uNzujN6BDGw4g41Vq15Q0ea7PRNsfBZZhPDN1zYTrfpdeRc9hMzS9eJsApnIH14z5F700AYnp5sJA"; // Replace with your Stripe secret key


    public String processPayment(PaymentRequest paymentRequest) {

        Stripe.apiKey = SECRET_KEY;

        if (paymentRequest == null || paymentRequest.getStripeTokenC() == null || paymentRequest.getStripeTokenC().isEmpty()) {
            return "Stripe token is missing.";
        }

        if (paymentRequest.getAmountC() <= 0) {
            return "Invalid amount.";
        }

        try {
            // Create a charge
            Charge charge = Charge.create(ChargeCreateParams.builder()
                    .setAmount((long) paymentRequest.getAmountC()) // Amount in cents
                    .setCurrency(paymentRequest.getCurrencyC())
                    .setSource(paymentRequest.getStripeTokenC())
                    .setDescription(paymentRequest.getDescriptionC())
                    .build());

            // Handle successful charge
            return "Payment successful! Charge ID: " + charge.getId();
        } catch (StripeException e) {
            // Handle Stripe exceptions
            return "Error processing payment: " + e.getMessage();
        }
    }

}
