package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import com.example.vitanovabackend.DAO.Entities.Tracker;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFoodService {
    public Food addFood(Food food , MultipartFile file) throws IOException;
    public Food updateFood(Food food,MultipartFile file) throws IOException;
    public void deleteFood(Long idFood);
    public void deleteFood2(Food food);
    public Food archiverFood(long id);
    public List<Food> getFood();
    public Food getFoodById(long id);
    /////////////////////////////////////////////
    public List<Tracker> addTracker  (List<Tracker> trackers);
    public List<Tracker> updateTracker  (List<Tracker> trackers);
    public void deleteTracker(List<Tracker> trackers);
    public Tracker archiverTracker(Long idTracker);
    public  List<Tracker> getTracker();
    //////////////////////////////////////////////
    public Hydration addHydra(Hydration hydration);
    public Hydration updateHydra(Hydration hydration);
    public void deleteHydra(Long id);
    public void deleteHydra2(Hydration hydration);
    public Hydration  archiverHydra(Long id);
    public List<Hydration> getHydra();

}
