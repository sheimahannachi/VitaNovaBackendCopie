package com.example.vitanovabackend.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.antlr.v4.runtime.misc.NotNull;

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

    @NotBlank(message = "Name is mandatory")
    String name ;

    //Active for 7 days
    LocalDate creationDate;

    boolean active;

    @NotBlank(message = "Description is mandatory")
    String description;

    @Enumerated(EnumType.STRING)
    ChallengeType type;

    @NotBlank(message = "Goal is mandatory")
    long goal;


    @NotBlank(message = "Compare is mandatory")
    @Enumerated(EnumType.STRING)
    ChallengeCompare compare;


    @ManyToOne(cascade = CascadeType.ALL)
    Community community ;



}
