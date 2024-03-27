package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Food")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)

public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @JsonProperty("Calories")
    double calories;
    @JsonProperty("Sugar")
    double glucides;
    @JsonProperty("Protein")
    double protein;
    @JsonProperty("Lipid")
    double lipides;
    @JsonProperty("Product")
    String title;
    @JsonProperty("FoodPic")
    String FoodPic;
    Boolean archive;
    @JsonProperty("Category")
    String category;
    @JsonProperty("Vitamin C")
    double vitaminC;
    @JsonProperty("Vitamin B6")
    double vitaminB6;
    @JsonProperty("Vitamin E")
    double vitaminE;
    @JsonProperty("Calcium")
    double calcium;
    @ManyToMany
    List<User> person=new ArrayList<>();
    @ManyToOne
    PeriodTracker periodTracker;

}
