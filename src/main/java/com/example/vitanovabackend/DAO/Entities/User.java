package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "User")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "email")
    private String email;
    @Column(name = "weight")
    private float weight;
    @Column(name = "height")
    private float height;
    @Column(name = "password")
    private String password;


    @OneToOne
    PersonalGoals personalGoals;

    @OneToOne(cascade = CascadeType.ALL)
    PeriodTracker periodTracker;

    @OneToOne(cascade = CascadeType.ALL)
    Cart cart;

    @ManyToMany
    List<Food>foods=new ArrayList<>();

    @ManyToMany( mappedBy = "membres",cascade = CascadeType.ALL)
    List<Community> communities = new ArrayList<>();
}