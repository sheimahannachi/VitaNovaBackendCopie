package com.example.vitanovabackend.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class CheckoutService implements ICheckoutService{
@Override
    public Session createCheckoutSession(Map<String, Object> params) throws StripeException {
        // Create a checkout session using Stripe API
        return Session.create(params);
    }
}
