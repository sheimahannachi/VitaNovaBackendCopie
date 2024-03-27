package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Table(name="orderr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Commandeline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idOrder;
    LocalDate dateOrder =null;
    float prixOrder ;
    float quantite;
    boolean archiveOrder;
    @ManyToOne
    Cart cart;
    @ManyToOne
    Product product;
}
