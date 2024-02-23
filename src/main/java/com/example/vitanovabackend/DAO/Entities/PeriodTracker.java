package com.example.vitanovabackend.DAO.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate StartDate;
    @Column(name = "EndDate")
    private LocalDate EndDate;
    @Column(name = "CycleLength")
    private Integer CycleLength;
    @Column(name = "MenstruationDuration")
    private Integer MenstruationDuration;
    @Column(name = "OvulationDate")
    private LocalDate OvulationDate;
    @Enumerated(EnumType.STRING)
    private Symptoms symptoms;
    @Enumerated(EnumType.STRING)
    private Mood mood;



    @OneToOne(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<WorkoutProgram> workoutPrograms = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<Food> foods = new ArrayList<>();
}
