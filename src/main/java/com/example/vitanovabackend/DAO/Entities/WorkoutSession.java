package com.example.vitanovabackend.DAO.Entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "WorkoutSession")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class WorkoutSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    LocalDateTime time_start;
    LocalDateTime time_end;
    @Enumerated(EnumType.STRING)
    Intensity intensity;
    @ManyToOne
    @JoinColumn(name = "idUser")
    User user;
}
