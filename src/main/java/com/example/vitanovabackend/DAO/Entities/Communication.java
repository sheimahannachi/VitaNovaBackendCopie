package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

        @NotNull(message = "Date is Mendatory")
        LocalDate sentDate;

        boolean seen;


        @Column(nullable = true)
        String imageSent;






        @NotNull(message = "Sender is Mendatory")
        @ManyToOne
        User sender;

        @ManyToOne
        User reciever;


        @ManyToOne
        Community community;

    }
