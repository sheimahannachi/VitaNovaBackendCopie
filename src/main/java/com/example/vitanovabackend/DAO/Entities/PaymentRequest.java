package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {


        private int amountC;
        private String nameC;
        private String cardNumberC;
        private String expDateC;
        private String cvcC;
        private String stripeTokenC;
        private String currencyC;
        private String descriptionC;


}

