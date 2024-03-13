package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Tracker")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String consumedfood;//list of food
    double consumedcalories;
    Boolean archive;
    String notification;
    LocalDate date ;
    @OneToOne (mappedBy = "tracker")
    PersonalGoals personalGoals;

    @OneToOne
    Hydration hydration;

}
