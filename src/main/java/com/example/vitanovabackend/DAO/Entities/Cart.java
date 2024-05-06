package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    Long idCart;
    float priceCart=0;
    LocalDate dateCart;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Commandeline> commandelineList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "cart")
    @JsonBackReference
    User user;
}
