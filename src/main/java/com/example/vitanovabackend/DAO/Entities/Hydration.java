package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Table(name = "Hydration")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Hydration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int cupsqte;
    double sumofwater;
    String notification;
    LocalDate date;
    Boolean archive;

    @OneToOne(mappedBy = "hydration")
    Tracker tracker;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
