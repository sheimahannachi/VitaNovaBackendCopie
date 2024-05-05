package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "weightStart")
    private float weightStart;
    @Column(name = "dateGoal")
    private LocalDate dateGoal;
    @Column(name = "description")
    private String description;
    @Column(name = "DailyNeededCalories")
    private float DailyNeededCalories;
    private LocalDate StartDate=LocalDate.now();

    @OneToOne(mappedBy = "personalGoals")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy ="personalGoal")
    List<WorkoutProgram> workoutProgramList=new ArrayList<>();
    @OneToOne
    Tracker tracker;
}