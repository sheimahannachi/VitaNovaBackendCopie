package com.example.vitanovabackend.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaymentPayload {
    private int amount;
    private String name;
    private String cardNumber;
    private String expDate;
    private String cvc;
    private String stripeToken;
}