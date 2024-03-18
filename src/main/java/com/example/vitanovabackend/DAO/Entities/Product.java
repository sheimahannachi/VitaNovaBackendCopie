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
    Long idPr;
    String namePr;

    String categoriePr;
    float pricePr ;
    String picturePr;
    String descriptionPr ;
    int quantityPr;
    String statusPr;
    boolean archivePr;
    int likesCount;

}