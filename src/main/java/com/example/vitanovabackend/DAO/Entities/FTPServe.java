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
public class FTPServe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFTP;


    private String ipAddress;

    private String userFTP;


    private String passwordFTP;
}
