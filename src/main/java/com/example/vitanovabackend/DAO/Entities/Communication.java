package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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


        @NotBlank(message = "Message is mandatory")
        String message;

        @NotBlank(message = "Message is mandatory")
        LocalDate sentDate;

        boolean seen;


        @NotBlank(message = "Message is mandatory")
        @ManyToOne
        User sender;

        @OneToOne
        User reciever;

    }
