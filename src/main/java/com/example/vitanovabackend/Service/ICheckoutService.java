package com.example.vitanovabackend.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.Map;

public interface ICheckoutService {
    Session createCheckoutSession(Map<String, Object> params) throws StripeException;
}
