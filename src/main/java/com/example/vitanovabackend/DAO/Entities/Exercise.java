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
    String exerciseName;
    String description;
    String typeEx;
    String sets;
    String reps;
    String instruction;

    @ManyToMany
    List<WorkoutProgram>workoutPrograms=new ArrayList<>();
}