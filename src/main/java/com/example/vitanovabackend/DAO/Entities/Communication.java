package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Communication {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        long id;


        String message;

        LocalDate sentDate;


        @OneToOne
        User sender;

        @OneToOne
        User reciever;

    }
