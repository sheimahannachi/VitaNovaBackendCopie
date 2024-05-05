package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Exercises")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;

    @Column(length = 1000) // Setting maximum length to 1000 characters
    String description;
    String typeEx;
    String bodypart;
    @Enumerated(EnumType.STRING)
    Intensity intensity ;
    String sets;
    String reps;
    //String instruction;
    String picture;
    boolean archived;
    double rate;


    @ManyToMany
    List<WorkoutProgram>workoutPrograms=new ArrayList<>();

    @ManyToOne
    PeriodTracker periodTracker;
}