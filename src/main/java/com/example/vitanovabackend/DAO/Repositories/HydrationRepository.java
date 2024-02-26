package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HydrationRepository extends JpaRepository<Hydration,Long> {
}
