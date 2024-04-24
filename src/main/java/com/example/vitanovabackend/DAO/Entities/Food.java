package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Food")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)

public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double calories;

    double glucides;

    double protein;

    double lipides;

    String title;

    String FoodPic;
    Boolean archive;

    String category;

    double vitaminC;

    double vitaminB6;

    double vitaminE;

    double calcium;
    @ManyToMany
    List<User> person=new ArrayList<>();
    @ManyToOne
    PeriodTracker periodTracker;


}
