package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long IdPr;
    @Enumerated(EnumType.STRING)
    Categories ProductCat;
    float Price ;
    @ManyToOne
    Cart cart;


}