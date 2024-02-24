package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "PersonalGoals")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalGoals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPG;
    @Column(name = "weightGoal")
    private float weightGoal;
    @Column(name = "dateGoal")
    private LocalDate dateGoal;
    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "personalGoals")
    User user;
    @OneToMany(mappedBy ="personalGoal")
    List<WorkoutProgram> workoutProgramList=new ArrayList<>();
    @OneToOne
    Tracker tracker;
}