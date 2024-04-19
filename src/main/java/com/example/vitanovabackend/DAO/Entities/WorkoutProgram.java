package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Table(name = "WorkoutProgram")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class WorkoutProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String title;
    @Enumerated(EnumType.STRING)
    Intensity intensity;
    String duration;
    String restIntervals;
    String restIntervalsBetweenSets;
    String sportType;
    String progression;
    String image;
    Boolean archived;

    @ManyToMany
    List<Exercise>exercises=new ArrayList<>();
    @ManyToOne
    PersonalGoals personalGoal;


}