package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idRate;
    int rate;
    @ManyToOne
    @JoinColumn(name = "idUser")
    User user;
    @ManyToOne
    @JoinColumn(name = "id")
    Exercise exercise;
}
