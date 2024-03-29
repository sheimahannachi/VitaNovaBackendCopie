package com.example.vitanovabackend.DAO.Entities;

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
    String code;
    double calories;
    double glucides;
    double protein;
    double lipides;
    String title;
    String ingredients;
    String FoodPic;
    LocalDate date;
    Boolean archive;
    String category;
    @ManyToMany
    List<User> person=new ArrayList<>();
    @ManyToOne
    PeriodTracker periodTracker;

}
