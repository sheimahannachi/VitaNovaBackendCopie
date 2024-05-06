package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.Service.IFoodService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @PostMapping("addTracker/{id}")
    public Tracker addTracker(Tracker tracker,@PathVariable("id") long id) {
        return iFoodService.addTracker(tracker,id);
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
    @PostMapping("addHydration/{id}")
    public Hydration addHydra( @PathVariable("id") long id){
        return iFoodService.addHydra(id);
    }
    @GetMapping("getHydration/{id}")
    public Hydration getHydrationForToday(@PathVariable("id") long userId) {
        // Get today's date
       return iFoodService.getHydrationForToday(userId);
    }
    @PutMapping("hydration/{userId}")
    public Hydration deleteHydration(@PathVariable long userId) {
        // Call the deleteHydration method from FoodService
       return iFoodService.deleteHydration(userId);
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
    @PostMapping("ListTracker/{id}")
    public void addFoodCards(@RequestParam List<Food> foods, @RequestParam int quantity, @RequestParam MealType mealType,@PathVariable("id")long id){
        iFoodService.addFoodCards(foods, quantity,mealType,id);

    }
    @GetMapping("get-food-cards")
    public List<FoodCard> getFoodCards() {
        return iFoodService.getFoodCards();
    }
    @DeleteMapping("deleteFoodCard")
    void deleteFoodCard(@RequestBody FoodCard foodCard){iFoodService.deleteFoodCard(foodCard);}
    @PutMapping("updateFoodCard/{id}")
    public void updateFoodCard(@RequestParam List<Food> foods,@RequestParam int quantity){iFoodService.updateFoodCard(foods, quantity);}


    @GetMapping("/ScanBarcode")
    public ResponseEntity<String> getProductInfo(@RequestParam("barcode") String barcode) {
        try {
            System.out.println("in scanBarcode Controller");
            String response = iFoodService.getProductInfo(barcode);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://real-time-image-search.p.rapidapi.com/search?" +
                        "query=" + query + // add query parameter to URL
                        "&region=us")
                .get()
                .addHeader("X-RapidAPI-Key", "d9b3426de0mshca06726a2e3de22p13b8a7jsn8ddd27ae9d20")
                .addHeader("X-RapidAPI-Host", "real-time-image-search.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    //d9b3426de0mshca06726a2e3de22p13b8a7jsn8ddd27ae9d20
//834be39b8fmshb04d4bb79324864p180dd4jsn4f60a3c304c1
    //13a325e426msh2277f99c6fef7b5p1d0f1djsn1bb5fb4cbef9
    private static final String API_KEY = "d9b3426de0mshca06726a2e3de22p13b8a7jsn8ddd27ae9d20";
    private static final String API_HOST = "real-time-image-search.p.rapidapi.com";
    private static final String API_PATH = "/search";

    @GetMapping("/search-image")
    @ResponseBody
    public ResponseEntity<String> searchImage(@RequestParam("query") String query, @RequestParam(value = "page", required = false, defaultValue = "1") int page) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://" + API_HOST + API_PATH + "?" +
                        "query=" + query + // add query parameter to URL
                        "&page=" + page + // add page parameter to URL
                        "&image_type=photo") // search for images only
                .get()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            JsonNode dataNode = jsonNode.get("data");
            if (dataNode.isArray() && dataNode.size() > 0) {
                JsonNode firstImageNode = dataNode.get(0);
                String imageUrl = firstImageNode.get("url").asText();
                // Return JSON response
                return ResponseEntity.ok().body("{\"url\": \"" + imageUrl + "\"}");
            } else {
                // Return empty JSON object
                return ResponseEntity.ok().body("{}");
            }
        }
    }




}