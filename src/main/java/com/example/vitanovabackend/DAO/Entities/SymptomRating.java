package com.example.vitanovabackend.DAO.Entities;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymptomRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symptom")
    private String symptom;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY) // Many SymptomRating entries can be associated with one PeriodTracker
    @JoinColumn(name = "idPeriod") // Name of the foreign key column in SymptomRating table
    private PeriodTracker periodTracker;
}
