package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Food;
import com.example.vitanovabackend.DAO.Entities.Hydration;
import com.example.vitanovabackend.DAO.Entities.Tracker;
import com.example.vitanovabackend.DAO.Repositories.FoodRepository;
import com.example.vitanovabackend.DAO.Repositories.HydrationRepository;
import com.example.vitanovabackend.DAO.Repositories.TrackerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
@Slf4j

public class FoodService implements IFoodService {
    FoodRepository foodRepository;
    TrackerRepository trackerRepository;
    HydrationRepository hydrationRepository;

    @Override
    public Food addFood(Food food ,MultipartFile file) throws IOException
    {
        String fileName = saveFile(file,"uploads");
        food.setFoodPic(fileName);
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Food food , MultipartFile file) throws IOException
    {
        String fileName = saveFile(file,"uploads");
        food.setFoodPic(fileName);
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
    public List<Food> getFood() {
        return foodRepository.findAll();
    }
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<Tracker> addTracker(List<Tracker> trackers) {
        return trackerRepository.saveAll( trackers);
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
}
