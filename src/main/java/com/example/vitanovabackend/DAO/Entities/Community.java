package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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


    @Pattern(regexp = "\\D+",message = "No numbers allowed in community name!")
    @NotBlank(message = "Community Name is mandatory!")
    @Size(min = 2,max = 30,message = "Invalid Community name size")
    String communityName;


    @NotBlank(message = "Community description is mandatory!")
    @Size(min = 5,max = 40,message = "Invalid Community description length")
    String description;

    LocalDate creationDate;


    boolean status ;



    @NotNull(message = "Community should have a creator!")
    @OneToOne
    User creator;


    @JsonManagedReference("communityBackRef")
    @OneToMany(mappedBy = "community")
    List<User> membres = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy ="community", cascade = CascadeType.ALL)
    List<Challenges> challenges=new ArrayList<>();
}

