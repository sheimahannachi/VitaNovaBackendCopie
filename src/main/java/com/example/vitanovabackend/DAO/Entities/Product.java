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
    @Enumerated(EnumType.STRING)
    Categories categoriePr;
    float pricePr ;
    String picturePr;
    String description ;
    boolean archivePr;
    int likesCount;


}