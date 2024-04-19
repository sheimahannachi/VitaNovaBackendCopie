package com.example.vitanovabackend.DAO.Entities;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;
>>>>>>> main
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.LocalDateTime;

>>>>>>> main
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
<<<<<<< HEAD


=======
    private LocalDateTime DateVerification= LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
>>>>>>> main


}
