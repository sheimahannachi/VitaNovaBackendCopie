package com.example.vitanovabackend.Service;

import com.stripe.exception.StripeException;

public interface StripeIService {

    public void createCharge(double amount, String currency) throws StripeException;
}
