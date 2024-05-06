package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IFoodService {
    public Food addFood(Food food , MultipartFile file) throws IOException;
    public Food updateFood(Food food,MultipartFile file) throws IOException;
    public void deleteFood(Long idFood);
    public void deleteFood2(Food food);
    public Food archiverFood(long id);
    public Page<Food> getFood(int page, int size);
    public Food getFoodById(long id);
    /////////////////////////////////////////////
    public Tracker addTracker(Tracker tracker, Long userId);
    public List<Tracker> updateTracker  (List<Tracker> trackers);
    public void deleteTracker(List<Tracker> trackers);
    public Tracker archiverTracker(Long idTracker);
    public  List<Tracker> getTracker();
    //////////////////////////////////////////////
    public Hydration addHydra(long id);
    public Hydration updateHydra(Hydration hydration);
    public void deleteHydra(Long id);
    public void deleteHydra2(Hydration hydration);
    public Hydration  archiverHydra(Long id);
    public List<Hydration> getHydra();
    public Map<LocalDate, Double> calculateConsumedCaloriesPerDay(List<Tracker> trackers);
    public void addFoodListToTracker(Tracker tracker, Map<Food, Integer> foodQuantityMap);
    public void addFoodCards(List<Food> foods, int quantity, MealType mealType, long userId);
    public List<FoodCard> getFoodCards();
    public void deleteFoodCard(FoodCard foodCard);
    public void updateFoodCard(List<Food> foods, int quantity);
    /////////

    public List<FoodCard> getFoodCardsByMealType( MealType mealType,
                                                  Long idTracker);

    String getProductInfo(String barcode) throws IOException;
    public Hydration getHydrationForToday(long userId);
    public Hydration deleteHydration(long userId);
}
