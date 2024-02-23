package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdCard;
    float PriceCard;
    LocalDate DateCard;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cart")
    List<Product>products = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "cart")
    User user;
}
