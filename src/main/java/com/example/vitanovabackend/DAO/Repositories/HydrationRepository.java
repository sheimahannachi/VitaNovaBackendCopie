package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HydrationRepository extends JpaRepository<Hydration,Long> {


    Optional<Hydration> findByUser_IdUserAndDate(long id, LocalDate date);


}
