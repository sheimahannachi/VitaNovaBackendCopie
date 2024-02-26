package com.example.vitanovabackend.DAO.Controller;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import com.example.vitanovabackend.DAO.Entities.Tracker;
import com.example.vitanovabackend.DAO.Services.IFoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("RestController")
public class FoodController {

    IFoodService iFoodService;
    @PostMapping("addFood")
    public Food addFood(@ModelAttribute Food food ,@RequestParam("pic") MultipartFile file) throws IOException
    {
        return iFoodService.addFood(food,file);
    }
    @PutMapping("updateFood")
    public Food updateFood(@ModelAttribute Food food ,@RequestParam("pic") MultipartFile file) throws IOException
    {
        return iFoodService.updateFood(food,file);
    }
    @DeleteMapping("deleteFood/{id}")
    public void deleteFood(@PathVariable("id") Long idFood) {

        iFoodService.deleteFood(idFood);
    }
    @DeleteMapping("deleteFood")
    public void deleteFood2(Food food) {

        iFoodService.deleteFood2(food);
    }
    @DeleteMapping("archiveFood/{id}")
    public Food archiverFood(@PathVariable("id") Long id){
        return iFoodService.archiverFood(id);
    }
    @GetMapping("getFood")
    public List<Food> getFood() {

        return iFoodService.getFood();
    }
    //////////////////////////////////////////////
    @PostMapping("addTracker")
    public List<Tracker> addTracker(List<Tracker> trackers) {
        return iFoodService.addTracker(trackers);
    }
    @PutMapping("updateTracker")
    public List<Tracker> updateTracker(List<Tracker> trackers){
        return iFoodService.updateTracker(trackers);
    }
    @DeleteMapping("deleteTracker")
    public void deleteTracker(List<Tracker> trackers){
        iFoodService.deleteTracker(trackers);
    }
    @DeleteMapping("archiveTracker/{id}")
    public Tracker archiverTracer(@PathVariable("id") Long idTracker){
        return iFoodService.archiverTracker(idTracker);
    }
    @GetMapping("getTracker")
    public List<Tracker> getTracker(){
        return iFoodService.getTracker();
    }
    /////////////////////////////////////////////////
    @PostMapping("addHydration")
    public Hydration addHydra(Hydration hydration){
        return iFoodService.addHydra(hydration);
    }
    @PutMapping("updateHydration")
    public Hydration updateHydra(Hydration hydration){
        return iFoodService.updateHydra(hydration);
    }
    @DeleteMapping("deleteHydration/{id}")
    public void deleteHydra(@PathVariable("id") Long id){
        iFoodService.deleteHydra(id);
    }
    @DeleteMapping("deleteHydration")
    public void deleteHydra2(Hydration hydration){
        iFoodService.deleteHydra2(hydration);
    }
    @DeleteMapping("archiveHydration/{id}")
    public Hydration archiverHydra(@PathVariable("id") Long id){
        return iFoodService.archiverHydra(id);
    }
    @GetMapping("getHydration")
    public List<Hydration> getHydra(){
        return iFoodService.getHydra();
    }


}
