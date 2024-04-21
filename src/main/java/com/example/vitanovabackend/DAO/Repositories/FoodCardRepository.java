package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.FoodCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCardRepository extends JpaRepository<FoodCard,Long> {
    @Query("SELECT f FROM FoodCard f WHERE f.tracker.id IS NULL")
    List<FoodCard> findFoodCardWithNullTrackerId();
}
