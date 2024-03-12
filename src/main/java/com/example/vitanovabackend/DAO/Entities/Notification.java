package com.example.vitanovabackend.DAO.Entities;

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
    private long id;
    @Column(name = "Content")
    private String Content;
    @Column(name = "priority")
    private String priority;
    @Column(name = "NotificationDate")
    private LocalDate NotificationDate;
    @Column(name = "archive")
    private Boolean archive=false;


    @ManyToOne
    PeriodTracker periodTracker;
}
