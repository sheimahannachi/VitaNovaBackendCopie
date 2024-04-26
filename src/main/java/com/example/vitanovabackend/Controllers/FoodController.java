package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.Service.IFoodService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("RestController")
public class FoodController {

    IFoodService iFoodService;
    @PostMapping("addFood")
    public Food addFood(@ModelAttribute Food food ,@RequestParam("pic") MultipartFile file) throws IOException
    {
        return iFoodService.addFood(food,file);
    }
    @PutMapping("updateFood/{id}")
    public Food updateFood(@PathVariable("id") long id,@ModelAttribute Food food ,@RequestParam("pic") MultipartFile file) throws IOException
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
    public Page<Food> getFood(int page,int size) {

        return iFoodService.getFood(page, size);
    }
    @GetMapping("getFood/{id}")
    public Food getFoodById(@PathVariable("id") long id){
        return iFoodService.getFoodById(id);
    }
    @GetMapping("/lookup")
    public Object lookupBarcode(@RequestParam String upc) {
        RestTemplate restTemplate = new RestTemplate();
        String barcodeApiUrl = "https://api.upcitemdb.com";
        String url = barcodeApiUrl + "/prod/trial/lookup?upc=" + upc;
        return restTemplate.getForObject(url, Object.class);
    }
    //////////////////////////////////////////////
    @PostMapping("addTracker")
    public Tracker addTracker(Tracker tracker) {
        return iFoodService.addTracker(tracker);
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

    @GetMapping("getcalories")
    public Map<LocalDate, Double> calculateConsumedCaloriesPerDay(@RequestParam List<Tracker> trackers) {


        return iFoodService.calculateConsumedCaloriesPerDay(trackers);
    }
    @PostMapping("ListTracker")
    public void addFoodCards(@RequestParam List<Food> foods, @RequestParam int quantity, @RequestParam MealType mealType){
        iFoodService.addFoodCards(foods, quantity,mealType);

    }
    @GetMapping("get-food-cards")
    public List<FoodCard> getFoodCards() {
        return iFoodService.getFoodCards();
    }
    @DeleteMapping("deleteFoodCard")
    void deleteFoodCard(@RequestBody FoodCard foodCard){iFoodService.deleteFoodCard(foodCard);}
    @PutMapping("updateFoodCard/{id}")
    public void updateFoodCard(@RequestParam List<Food> foods,@RequestParam int quantity){iFoodService.updateFoodCard(foods, quantity);}

}