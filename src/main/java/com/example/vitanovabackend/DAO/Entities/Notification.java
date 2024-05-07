package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Table(name = "Notification")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(name = "Content")
    private String Content;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "categories")
    private List<String> categories = new ArrayList<>();
    @Column(name = "archive")
    private Boolean archive=false;
    @Column(name = "subscription")
    private Boolean subscription=false;




    @JsonManagedReference("notifBack")
    @ManyToOne
    User user;



}
