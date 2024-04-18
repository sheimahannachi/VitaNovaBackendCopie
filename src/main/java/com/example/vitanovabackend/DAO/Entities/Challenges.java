package com.example.vitanovabackend.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "\\D+",message = "No numbers allowed in community name!")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2 , max = 30 , message = "Invalid name size!")
    String name ;


    //Active for 7 days
    LocalDate creationDate;

    boolean active;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 2 , max = 50 , message = "Invalid description size!")
    String description;

    @NotNull(message = "Challenge type is mandatory!")
    @Enumerated(EnumType.STRING)
    ChallengeType type;

    @NotNull(message = "Goal is mandatory")
    long goal;


    @NotNull(message = "Compare is mandatory")
    @Enumerated(EnumType.STRING)
    ChallengeCompare compare;


    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    Community community ;



}
