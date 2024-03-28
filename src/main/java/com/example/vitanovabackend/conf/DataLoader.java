package com.example.vitanovabackend.conf;

import com.example.vitanovabackend.DAO.Repositories.FoodRepository;
import com.example.vitanovabackend.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataLoader implements ApplicationRunner {

    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @Autowired
    public DataLoader(FoodService foodService, FoodRepository foodRepository) {
        this.foodService = foodService;
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (foodRepository.count() == 0) {
            try {
                foodService.importFoodsFromJson();
            } catch (IOException e) {
                // Handle exception appropriately
                e.printStackTrace();
            }
        } else {
            System.out.println("Database already contains data. Skipping data import.");
        }
    }
}
