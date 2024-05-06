package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Table(name="Commandeline")
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
    String pictureOrder;
    String productName;
    LocalDate dateOrder =null;
    float prixOrder ;
    float totalOrder ;
    float quantity;
    boolean archiveOrder;
    @ManyToOne
    @JsonIgnore
    private Cart cart;
    @ManyToOne
    Product product;
}
