package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    UserRepository userRepository;
    private final OkHttpClient client = new OkHttpClient();
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
    public Tracker addTracker(Tracker tracker, Long userId) {
        // Retrieve food cards with null tracker_id
        List<FoodCard> foodCards = foodCardRepository.findFoodCardWithNullTrackerId();

        if (foodCards != null && !foodCards.isEmpty()) {
            double totalCalories = 0;

            // Save the tracker first to ensure it's persisted
            tracker = trackerRepository.save(tracker);

            // Set tracker for each food card and calculate total calories
            for (FoodCard foodCard : foodCards) {
                foodCard.setTracker(tracker);
                totalCalories += foodCard.getCalcCalories();
            }

            // Set consumed calories and other properties for the tracker
            tracker.setConsumedcalories(totalCalories);
            tracker.setDate(LocalDate.now());
            tracker.setArchive(false);

            // Set the user for the tracker
            User user = userRepository.findById(userId).orElse(null);
            tracker.setUser(user);
        }

        // Save the updated food cards (if any)
        foodCardRepository.saveAll(foodCards);

        // Save the tracker
        return trackerRepository.save(tracker);
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
    public Hydration addHydra(long userId) {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Check if a hydration entry exists for the user and today's date
        Optional<Hydration> existingHydration = hydrationRepository.findByUser_IdUserAndDate(userId, today);

        // If a hydration entry exists, update it; otherwise, create a new one
        if (existingHydration.isPresent()) {
            Hydration hydration = existingHydration.get();
            int cupsQty = hydration.getCupsqte();
            double sumOfWater = hydration.getSumofwater();
            if (cupsQty < 4) {
                // Increment cups quantity
                hydration.setCupsqte(cupsQty + 1);
                // Increment sum of water by 0.5L
                hydration.setSumofwater(sumOfWater + 0.5);
            } else {
                // User has already consumed 4 cups today
                return null;
            }
            // Save and return the updated hydration
            return hydrationRepository.save(hydration);
        } else {
            // No hydration entry exists for today, create a new one
            Hydration newHydration = new Hydration();
            newHydration.setUser(userRepository.findById(userId).orElse(null)); // Set the user
            newHydration.setCupsqte(1); // Set cups quantity to 1
            newHydration.setSumofwater(0.5); // Set sum of water to 0.5
            newHydration.setDate(today); // Set the date
            return hydrationRepository.save(newHydration); // Save and return the new hydration
        }
    }

    public Hydration getHydrationForToday(long userId) {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Retrieve hydration entry for the user and today's date
        Optional<Hydration> hydration = hydrationRepository.findByUser_IdUserAndDate(userId, today);

        // If hydration entry exists for today, return it; otherwise, return null
        return hydration.orElse(null);
    }
        public Hydration deleteHydration(long userId) {
            // Get today's date
            LocalDate today = LocalDate.now();

            // Retrieve hydration entry for the user and today's date
            Optional<Hydration> hydrationOptional = hydrationRepository.findByUser_IdUserAndDate(userId, today);

            // If hydration entry exists, decrement sumofwater by 0.5 for each unfilled cup
            if (hydrationOptional.isPresent()) {
                Hydration hydration = hydrationOptional.get();
                int cupsQty = hydration.getCupsqte();
                double sumOfWater = hydration.getSumofwater();

                // Decrement sumofwater by 0.5 for each unfilled cup
                if (cupsQty > 0) {
                    // Decrement cups quantity
                    hydration.setCupsqte(cupsQty - 1);
                    // Decrement sum of water by 0.5L
                    hydration.setSumofwater(Math.max(0, sumOfWater - 0.5)); // Ensure it's not negative
                }

                // Save and return the updated hydration
                return hydrationRepository.save(hydration);
            }

            // If hydration entry doesn't exist, return null or handle as needed
            return null;
        }


    /////////////////////////////////////////////0////////////////////////////



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
    public void addFoodCards(List<Food> foods, int quantity, MealType mealType, long userId) {
        LocalDateTime entryTimestamp = LocalDateTime.now(); // Get current timestamp

        List<FoodCard> foodCards = new ArrayList<>();
        for (Food food : foods) {
            double calcCalories = food.getCalories() * quantity; // Calculating calcCalories
            FoodCard foodCard = FoodCard.builder()
                    // .tracker(tracker)
                    .food(food)
                    .mealType(mealType) // Set mealType
                    .quantity(quantity)
                    .calcCalories(calcCalories) // Setting calcCalories
                    .entryTimestamp(entryTimestamp)
                    .build();
            foodCards.add(foodCard);
        }

        // Set the user for each food card
        User user = userRepository.findById(userId).orElse(null);
        for (FoodCard foodCard : foodCards) {
            foodCard.setUser(user);
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

        for (Food food : foods) {
            // Check if a FoodCard with the same foodId already exists
            Optional<FoodCard> existingFoodCardOptional = foodCardRepository.findByFoodId(food.getId());

            if (existingFoodCardOptional.isPresent()) {
                // If FoodCard already exists, update its quantity and other attributes
                FoodCard existingFoodCard = existingFoodCardOptional.get();
                int updatedQuantity =  quantity;
                double calcCalories = existingFoodCard.getFood().getCalories() * updatedQuantity; // Recalculate calories
                existingFoodCard.setCalcCalories(calcCalories); // Update calcCalories
                existingFoodCard.setQuantity(updatedQuantity);
                existingFoodCard.setEntryTimestamp(entryTimestamp); // Update timestamp
                // You may need to update other attributes as well based on your requirements
                foodCardRepository.save(existingFoodCard); // Save updated FoodCard
            } else {
                // If FoodCard doesn't exist, create a new one
                FoodCard foodCard = FoodCard.builder()
                        .food(food)
                        .quantity(quantity)
                        .entryTimestamp(entryTimestamp)
                        .build();
                foodCardRepository.save(foodCard); // Save new FoodCard
            }
        }
    }


    public List<FoodCard> getFoodCardsByMealType( MealType mealType,
                                                  Long idTracker) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Retrieve food cards by meal type and associated tracker id
        List<FoodCard> foodCards = foodCardRepository.getFoodCardsByMealTypeAndTrackerId(mealType, idTracker);

        // Filter food cards based on timestamp (remove those not associated with today's date)
        foodCards.removeIf(foodCard -> !foodCard.getEntryTimestamp().toLocalDate().equals(currentDate));


        return foodCards;
    }

    @Override
    public String getProductInfo(String barcode) throws IOException {
        Request request = new Request.Builder()
                .url("https://dietagram.p.rapidapi.com/apiBarcode.php?name=" + barcode)
                .get()
                .addHeader("X-RapidAPI-Key", "d9b3426de0mshca06726a2e3de22p13b8a7jsn8ddd27ae9d20")
                .addHeader("X-RapidAPI-Host", "dietagram.p.rapidapi.com")
                .build();
        System.out.println(request);
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response);

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }


}
