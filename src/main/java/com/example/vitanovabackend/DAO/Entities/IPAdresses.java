package com.example.vitanovabackend.DAO.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPAdresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  private String value;

  private  String Location;
    private LocalDateTime DateVerification= LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
