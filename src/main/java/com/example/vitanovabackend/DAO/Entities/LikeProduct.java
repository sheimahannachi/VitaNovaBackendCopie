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
public class LikeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long likePr;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;


}
