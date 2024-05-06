package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "food_card")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    @ManyToOne
    @JoinColumn(name = "tracker_id")
    private Tracker tracker;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    private Integer quantity;
    private double calcCalories;


    @Column(name = "entry_timestamp")
    private LocalDateTime entryTimestamp;
}
