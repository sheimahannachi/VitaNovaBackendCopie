package com.example.vitanovabackend.DAO.Repositories;


import com.example.vitanovabackend.DAO.Entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    @Query("SELECT f FROM Food f WHERE f.archive = false")
    List<Food> findActiveFoods() ;
}
