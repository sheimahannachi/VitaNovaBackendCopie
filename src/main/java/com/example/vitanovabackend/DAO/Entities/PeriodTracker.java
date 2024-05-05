package com.example.vitanovabackend.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
    private String StartDate;
    @Column(name = "CycleLength")
    private Integer CycleLength;

    @ElementCollection
    @CollectionTable(name = "PeriodTracker_SymptomRating", joinColumns = @JoinColumn(name = "period_id"))
    @OneToMany(cascade = CascadeType.PERSIST) // Cascade persist operation
    private List<SymptomRating> symptomRatings = new ArrayList<>();

    @Column(name = "mood")
    private String mood ;
    @Column(name = "periodLength")
    private Integer periodLength ;
    @Column(name = "archive")
    private Boolean archive=false;
    @Column(name = "cyclePhase")
    private String cyclePhase;
    @Column(name = "medications")
    private String medications;







    @OneToOne(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    @JsonIgnore
    User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<Exercise> exercises = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "periodTracker")
    List<Food> foods = new ArrayList<>();


}
