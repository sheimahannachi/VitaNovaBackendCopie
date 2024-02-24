package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    String ComunityName;


    String description;

    LocalDate creationDate;


    boolean status ;

    @ManyToOne
    User creator;

    @ManyToMany
    List<User> membres = new ArrayList<>();
    @OneToMany(mappedBy ="community")
    List<Challenges> challenges=new ArrayList<>();
}

