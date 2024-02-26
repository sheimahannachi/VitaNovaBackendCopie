package com.example.vitanovabackend.DAO.Entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Challenges {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name ;

    //Active for 7 days
    LocalDate creationDate;

    boolean active;

    String description;

    ChallengeType type;

    long goal;

    @ManyToOne
    Community community ;



}
