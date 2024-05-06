package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name = "Tracker")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Hydration hydration;

    private double consumedcalories;
    private Boolean archive;
    private String notification;
    private LocalDate date;

    @OneToOne(mappedBy = "tracker")
    private PersonalGoals personalGoals;

    @OneToMany(mappedBy = "tracker")
    private List<FoodCard> foodCards = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}

