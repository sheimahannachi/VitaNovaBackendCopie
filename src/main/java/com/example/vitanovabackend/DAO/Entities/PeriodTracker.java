package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays; // Import Arrays
import java.util.List;

@Table(name = "PeriodTracker")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPeriod;
    @Column(name = "StartDate")
    private String StartDate;
    @Column(name = "CycleLength")
    private Integer CycleLength;

    @ElementCollection
    private List<String> symptoms = new ArrayList<>();

    @Column(name = "mood")
    private String mood ;
    @Column(name = "periodLength")
    private Integer periodLength ;
    @Column(name = "archive")
    private Boolean archive=false;
    @Column(name = "cyclePhase")
    private String cyclePhase;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    @JsonIgnore
    User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<WorkoutProgram> workoutPrograms = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<Food> foods = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<Notification> notifications = new ArrayList<>();
}
