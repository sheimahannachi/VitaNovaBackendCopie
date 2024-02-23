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
    @Enumerated(EnumType.STRING)
    Intensity intensity;
    String frequency;
    String duration;
    String restIntervals;
    String sportType;
    String progression;
    @ManyToMany
    List<Exercise>exercises=new ArrayList<>();
    @ManyToOne
    PersonalGoals personalGoal;
    @ManyToOne
    PeriodTracker periodTracker;

}