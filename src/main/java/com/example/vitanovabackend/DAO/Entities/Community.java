package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    String communityName;


    String description;

    LocalDate creationDate;


    boolean status ;

    @JsonIgnore
    @ManyToOne
    User creator;

    @JsonIgnore
    @OneToMany(mappedBy = "community")
    List<User> membres = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy ="community")
    List<Challenges> challenges=new ArrayList<>();
}

