package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.FoodCard;
import com.example.vitanovabackend.DAO.Repositories.FoodCardRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import com.example.vitanovabackend.DAO.Entities.Tracker;
import com.example.vitanovabackend.DAO.Repositories.FoodRepository;
import com.example.vitanovabackend.DAO.Repositories.HydrationRepository;
import com.example.vitanovabackend.DAO.Repositories.TrackerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;

@Service
@AllArgsConstructor
@Slf4j

public class FoodService implements IFoodService {
    FoodRepository foodRepository;
    TrackerRepository trackerRepository;
    HydrationRepository hydrationRepository;
    FoodCardRepository foodCardRepository;

    public static String uploadDirectory= "C:/xampp/htdocs/uploads";
    @Override
    public Food addFood(Food food ,MultipartFile file) throws IOException
    {
        String fileName = saveFile(file,uploadDirectory);
        food.setFoodPic(fileName);
        food.setArchive(false);
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Food food , MultipartFile file) throws IOException
    {
        String fileName = saveFile(file,uploadDirectory);
        food.setFoodPic(fileName);
        food.setArchive(false);
        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long idFood) {
        foodRepository.deleteById(idFood);
    }

    @Override
    public void deleteFood2(Food food) {
        foodRepository.delete(food);
    }

    @Override
    public Food archiverFood(long id) {
        Food food=foodRepository.findById(id).get();
        food.setArchive(true);
        return foodRepository.save(food);
    }


    @Override
    public Page<Food> getFood(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        return foodRepository.findActiveFoods(pageable);
    }

    @Override
    public Food getFoodById(long id) {
        return foodRepository.findById(id).get();
    }
    /////////////////////////////////////////////////////////////////////////

    @Override
    public Tracker addTracker(Tracker tracker) {
       /* List<Food> selectedFoods = tracker.getConsumedFood();
        double consumedCalories = calculateConsumedCalories(selectedFoods);
        tracker.setConsumedcalories(consumedCalories);*/
        // Assurez-vous que d'autres attributs de Tracker sont correctement définis

        return trackerRepository.save(tracker);
    }

    private double calculateConsumedCalories(List<Food> selectedFoods) {
        double totalCalories = 0;
        for (Food food : selectedFoods) {
            totalCalories += food.getCalories();
        }
        return totalCalories;
    }

    @Override
    public List<Tracker> updateTracker(List<Tracker> trackers) {
        return trackerRepository.saveAll(trackers);
    }

    @Override
    public void deleteTracker(List<Tracker> trackers) {
        trackerRepository.deleteAll(trackers);
    }

    @Override
    public Tracker archiverTracker(Long idTracker) {
        Tracker tracker=trackerRepository.findById(idTracker).get();
        tracker.setArchive(true);
        return trackerRepository.save(tracker);
    }


    @Override
    public List<Tracker> getTracker() {
        return trackerRepository.findAll();
    }

    /////////////////////////////////////////////////////////////////////////

    @Override
    public Hydration addHydra(Hydration hydration) {
        return hydrationRepository.save(hydration);
    }

    @Override
    public Hydration updateHydra(Hydration hydration) {
        return hydrationRepository.save(hydration);
    }

    @Override
    public void deleteHydra(Long id) {
    hydrationRepository.deleteById(id);
    }

    @Override
    public void deleteHydra2(Hydration hydration) {
    hydrationRepository.delete(hydration);
    }

    @Override
    public Hydration archiverHydra(Long id) {
        Hydration hydration=hydrationRepository.findById(id).get();
        hydration.setArchive(true);
        return hydrationRepository.save(hydration);
    }

    @Override
    public List<Hydration> getHydra() {
        return hydrationRepository.findAll();
    }

    @Override
    public Map<LocalDate, Double> calculateConsumedCaloriesPerDay(List<Tracker> trackers) {
        return null;
    }

    @Override
    public void addFoodListToTracker(Tracker tracker, Map<Food, Integer> foodQuantityMap) {

    }

    private String saveFile(MultipartFile file, String directoryName) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Le fichier doit être une image");
            }
           /* String[] allowedTypes = {"image/jpeg", "image/jpg", "image/gif"};
            boolean isValidType = false;
            for (String type : allowedTypes) {
                if (contentType.startsWith(type)) {
                    isValidType = true;
                    break;
                }
            }

            if (!isValidType) {
                throw new IllegalArgumentException("Le fichier doit être une image de type JPEG, PNG ou GIF");
            }*/
            String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            Path filePath = Paths.get(directoryName, fileName);
            Files.createDirectories(filePath.getParent()); // Create directories if they don't exist
            Files.write(filePath, file.getBytes());

            return fileName;
        } else {
            throw new IllegalStateException("Le fichier est vide ou nul");
        }
    }
    public void importFoodsFromJson() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        // Read the JSON file from the resources folder
        try (FileReader reader = new FileReader("src/main/resources/food.json")) {
            Object obj = parser.parse(reader);

            // Convert JSON array to a list of Food objects
            JSONArray jsonArray = (JSONArray) obj;
            List<Food> foods = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                Food food = new Food();
                // Parse JSON object and set attributes of Food
                food.setCategory((String) jsonObject.get("Category"));
                food.setTitle((String) jsonObject.get("Product"));
                food.setCalories(getDoubleValue(jsonObject, "Calories"));
                food.setGlucides(getDoubleValue(jsonObject, "Sugar"));
                food.setProtein(getDoubleValue(jsonObject, "Protein"));
                food.setLipides(getDoubleValue(jsonObject, "Lipid"));
                food.setVitaminB6(getDoubleValue(jsonObject, "Vitamin B6"));
                food.setVitaminC(getDoubleValue(jsonObject, "Vitamin C"));
                food.setVitaminE(getDoubleValue(jsonObject, "Vitamin E"));
                food.setCalcium(getDoubleValue(jsonObject, "Calcium"));
                food.setFoodPic((String) jsonObject.get("FoodPic"));
                // Similarly, parse other attributes

                // Assuming archive is a boolean attribute in your Food entity
                food.setArchive(false);
                foods.add(food);
            }

            // Save the mapped entities to the database
            foodRepository.saveAll(foods);
        }
    }

    private double getDoubleValue(JSONObject jsonObject, String key) {
        Object value = jsonObject.get(key);
        if (value != null && !value.toString().isEmpty()) {
            return Double.parseDouble(value.toString());
        }
        return 0.0; // or throw an exception if the value is mandatory
    }
    ///////////////////////////////////////////////////////////////////////////////
    public void addFoodCards(List<Food> foods, int quantity) {
        LocalDateTime entryTimestamp = LocalDateTime.now(); // Get current timestamp

        List<FoodCard> foodCards = new ArrayList<>();
        for (Food food : foods) {
            double calcCalories = food.getCalories() * quantity; // Calculating calcCalories
            FoodCard foodCard = FoodCard.builder()
                    // .tracker(tracker)
                    .food(food)
                    .quantity(quantity)
                    .calcCalories(calcCalories) // Setting calcCalories
                    .entryTimestamp(entryTimestamp)
                    .build();
            foodCards.add(foodCard);
        }

        foodCardRepository.saveAll(foodCards);
    }
    public List<FoodCard> getFoodCards() {
        return foodCardRepository.findFoodCardWithNullTrackerId();
    }

    @Override
    public void deleteFoodCard(FoodCard foodCard) {
        foodCardRepository.delete(foodCard);
    }

    @Override
    public void updateFoodCard(List<Food> foods, int quantity) {
        LocalDateTime entryTimestamp = LocalDateTime.now(); // Get current timestamp

        List<FoodCard> foodCards = new ArrayList<>();
        for (Food food : foods) {
            FoodCard foodCard = FoodCard.builder()
                    // .tracker(tracker)
                    .food(food)
                    .quantity(quantity)
                    .entryTimestamp(entryTimestamp)
                    .build();
            foodCards.add(foodCard);
        }

        foodCardRepository.saveAll(foodCards);
    }
}