package com.example.vitanovabackend.Service;
import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
@Slf4j


public class Workout implements Iworkout {
    WorkoutProgramRepository workoutProgramRepository;
    ExerciseRepository exerciseRepository;
    UserExerciseRatingRepository userExerciseRatingRepository;
    UserRepository userRepository;
    WorkoutSessionRepository workoutSessionRepository;

    public static String uploadDirectory = "C:/xampp/htdocs/uploads";

    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file, String[] selectedExerciseIds) throws IOException {
        // Save the uploaded image
        String fileName = saveFile(file, uploadDirectory);
        workoutProgram.setImage(fileName);

        // Fetch selected exercises by their IDs
        List<Exercise> selectedExercises = new ArrayList<>();
        int totalDuration = 0; // Initialize total duration

        for (String exerciseId : selectedExerciseIds) {
            Exercise exercise = exerciseRepository.findById(Long.parseLong(exerciseId))
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found with ID: " + exerciseId));

            // Parse sets and reps from strings in the format "3-4" and "12-15"
            String setsString = exercise.getSets();
            String repsString = exercise.getReps();

            int setsStart = Integer.parseInt(setsString.split("-")[0]);
            int setsEnd = Integer.parseInt(setsString.split("-")[1]);

            int repsStart = Integer.parseInt(repsString.split("-")[0]);
            int repsEnd = Integer.parseInt(repsString.split("-")[1]);

            // Check if sets and reps fall within the specified range
            if (setsStart >= 3 && setsEnd <= 4 && repsStart >= 12 && repsEnd <= 15) {
                // Calculate duration for each exercise (including rest intervals between sets)
                totalDuration += ((setsStart + setsEnd) / 2) * ((repsStart + repsEnd) / 2) * 2; // Each set is 2 minutes (1 minute exercise + 45 seconds rest)
                totalDuration += 45 * (setsEnd - 1); // Add rest intervals between sets (45 seconds rest between each set)
            }

            selectedExercises.add(exercise);
        }

        // Associate fetched exercises with the workout program
        workoutProgram.setExercises(selectedExercises);

        // Set rest intervals
        workoutProgram.setRestIntervals("60s");
        workoutProgram.setRestIntervalsBetweenSets("45s");

        // Convert total duration to a formatted string (assuming it represents minutes)
        int totalMinutes = totalDuration / 60;
        int totalSeconds = totalDuration % 60;
        String formattedDuration = totalMinutes + " minutes " + totalSeconds + " seconds";

        // Set the formatted duration for the workout program
        workoutProgram.setDuration(formattedDuration);

        // Save the workout program with associated exercises
        return workoutProgramRepository.save(workoutProgram);
    }


    private List<Exercise> mergeLists(List<Exercise> list1, List<Exercise> list2) {
        // Implement your merging logic here
        // For example, you can simply add all exercises from both lists to a new list
        List<Exercise> mergedList = new ArrayList<>(list1);
        mergedList.addAll(list2);
        return mergedList;
    }

   /* @Override
    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException {
        return null;
    }*/

    @Override
    public WorkoutProgram UpdatePlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException {
        String fileName = saveFile(file, uploadDirectory);
        workoutProgram.setImage(fileName);
        return workoutProgramRepository.save(workoutProgram);
    }

    public WorkoutProgram ArchiverPlan(long id) {
        WorkoutProgram workoutProgram = workoutProgramRepository.findById(id).get();
        workoutProgram.setArchived(true);
        return workoutProgramRepository.save(workoutProgram);
    }

    @Override
    public void DeletePlan(WorkoutProgram workoutProgram) {

        workoutProgramRepository.delete(workoutProgram);
    }

    @Override
    public Page<WorkoutProgram> GetPlan(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workoutProgramRepository.findActiveWorkoutPlan(pageable);
    }

    public WorkoutProgram GetPlanById(long id) {
        return workoutProgramRepository.findById(id).get();
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


    @Override
    public Exercise addExercise(Exercise exercise, MultipartFile file) throws IOException {
        String fileName = saveFile(file, uploadDirectory);
        exercise.setArchived(false);
        exercise.setPicture(fileName);
        return exerciseRepository.save(exercise);
    }


    @Override
    public Exercise UpdateExercise(Exercise exercise, MultipartFile file) throws IOException {
        String fileName = saveFile(file, uploadDirectory);
        exercise.setPicture(fileName);
        return exerciseRepository.save(exercise);
    }

    public Exercise ArchiverExercise(long id) {
        Exercise exercise = exerciseRepository.findById(id).get();
        exercise.setArchived(!exercise.isArchived());
        return exerciseRepository.save(exercise);
    }

    @Override
    public void DeleteExercise(Exercise exercise) {
        exerciseRepository.delete(exercise);
    }

    @Override
    public Page<Exercise> GetExercise(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exerciseRepository.findAll(pageable);
    }

    public Page<Exercise> GetActiveExercise(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exerciseRepository.findActiveExercises(pageable);
    }

    /*public Exercise rateExercise(long id,int rate){
        Exercise exercise=exerciseRepository.findById(id).get();
        exercise.setRate(rate);
        return exerciseRepository.save(exercise);
    }
*/
    public UserRating saveUserExerciseRating(UserRating userExerciseRating, long userId, long idExercise) {
        // Find the exercise by its ID
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(idExercise);
        if (exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();

            // Find the user by their ID
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Check if the user has already rated the exercise
                Optional<UserRating> existingRating = userExerciseRatingRepository.findByUserAndExercise(user, exercise);

                if (existingRating.isPresent()) {
                    // Update the existing rating
                    UserRating ratingToUpdate = existingRating.get();
                    ratingToUpdate.setRate(userExerciseRating.getRate()); // Update the rating value if needed
                    return userExerciseRatingRepository.save(ratingToUpdate);
                } else {
                    // Associate the exercise and user with the user rating
                    userExerciseRating.setExercise(exercise);
                    userExerciseRating.setUser(user);

                    // Save the user exercise rating
                    return userExerciseRatingRepository.save(userExerciseRating);
                }
            } else {
                // Handle the case where user is not found
                return null;
            }
        } else {
            // Handle the case where exercise is not found
            return null;
        }

    }


    public Exercise getExerciseById(long id) {
        return exerciseRepository.findById(id).get();
    }

    public double calculateAverageRating(long exerciseId) {
        // Retrieve all ratings for the exercise
        List<UserRating> ratings = userExerciseRatingRepository.findByExerciseId(exerciseId);

        // Calculate the total sum of ratings
        int totalRatings = ratings.size();
        if (totalRatings == 0) {
            return 0; // Return 0 if there are no ratings
        }

        int sumOfRatings = ratings.stream().mapToInt(UserRating::getRate).sum();

        // Calculate the average rating
        double averageRating = (double) sumOfRatings / totalRatings;

        // Retrieve the corresponding Exercise entity from the database
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        if (exercise != null) {
            // Update the rating attribute of the Exercise entity
            exercise.setRate(averageRating);

            // Save the updated Exercise entity back to the database
            exerciseRepository.save(exercise);
        } else {
            // Handle the case where the exercise is not found
        }

        return averageRating;
    }

    public void importExercisesFromCsv() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/exercises (1).csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.size() >= 10) { // Ensure the row has at least 10 values
                    Exercise exercise = new Exercise();
                    exercise.setTitle(csvRecord.get("Title"));
                    exercise.setDescription(csvRecord.get("Desc"));
                    exercise.setArchived(false);
                    exercise.setBodypart(csvRecord.get("BodyPart"));
                    exercise.setTypeEx(csvRecord.get("Type"));
                    exercise.setReps(csvRecord.get("Reps"));
                    exercise.setSets(csvRecord.get("Sets"));
                    exercise.setIntensity(Intensity.valueOf(csvRecord.get("Level").toUpperCase()));
                    exercise.setPicture(csvRecord.get("Picture"));
                    // Map other fields as needed

                    exerciseRepository.save(exercise);
                } else {
                    // Handle rows with fewer values than expected
                    System.err.println("Skipping row with insufficient values: " + csvRecord);
                }
            }

            csvParser.close();
        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }
    }


   /* public Page<Exercise> searchExercises(String bodyParts, String searchText,int page,int size) {
        /*Pageable pageable = PageRequest.of(page, size);
        if (bodyParts == null && searchText == null) {
            return exerciseRepository.findAll(pageable);
        } else if (bodyParts != null && searchText != null) {
            return exerciseRepository.findByBodypartAndTitle(bodyParts, searchText,pageable);
        } else if (bodyParts != null) {
            return exerciseRepository.findByBodypart(bodyParts,pageable);
        } else {
            return exerciseRepository.findByTitle(searchText, pageable);
        }
    }*/

    public Page<Exercise> getFilteredExercises(int page, int size, List<String> bodyParts) {
        Pageable pageable = PageRequest.of(page, size);
        List<Exercise> allExercises = new ArrayList<>();
        for (String bodyPart : bodyParts) {
            Page<Exercise> exercisesForBodyPart = exerciseRepository.findByBodypart(bodyPart, pageable);
            allExercises.addAll(exercisesForBodyPart.getContent());
        }
        return new PageImpl<>(allExercises, pageable, allExercises.size());
    }

    public Page<Exercise> findExercisesOrderByAverageRating(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return exerciseRepository.findExercisesOrderByAverageRating(pageable);
    }

    @Override
    public WorkoutSession addWorkoutSession(WorkoutSession workoutSession, long id) {
        User user = userRepository.findById(id).get();
        workoutSession.setTime_start(LocalDateTime.now());
        workoutSession.setUser(user);
        // Save the workout session entity to the database
        return workoutSessionRepository.save(workoutSession);
    }

    public WorkoutSession addSession(WorkoutSession workoutSession, long id, Intensity intensity) {
        User user = userRepository.findById(id).get();
        WorkoutProgram workoutProgram = workoutProgramRepository.findByIntensity(intensity);
        workoutSession.setTime_start(LocalDateTime.now());
        workoutSession.setUser(user);
        workoutSession.setIntensity(workoutProgram.getIntensity());
        // Save the workout session entity to the database
        return workoutSessionRepository.save(workoutSession);
    }

    public Map<String, List<Object[]>> getUserTrainingStatistics(long userId) {
        List<WorkoutSession> resultList = workoutSessionRepository.getUserTrainingStatistics(userId);

        // Group the results by month-year
        Map<String, List<Object[]>> statisticsData = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (WorkoutSession workoutSession : resultList) {
            LocalDateTime timeStart = workoutSession.getTime_start();
            String monthYear = timeStart.format(formatter);
            Object[] result = new Object[] { workoutSession.getIntensity(), timeStart };
            statisticsData.computeIfAbsent(monthYear, k -> new ArrayList<>()).add(result);
        }
        return statisticsData;
    }

    public List<WorkoutSession> getAllWorkoutSessions(long id) {

        return workoutSessionRepository.findByUser_IdUser(id);
    }

    @Override
    public List<Object[]> getAllWorkoutSessionData(long id) {
        List<WorkoutSession> sessions = getAllWorkoutSessions(id);
        List<Object[]> sessionData = new ArrayList<>();

        for (WorkoutSession session : sessions) {
            Object[] data = new Object[3];
            data[0] = session.getUser().getIdUser(); // UserID
            data[1] = session.getIntensity();    // Intensity
            data[2] = session.getTime_start();   // Start Time
            sessionData.add(data);
        }

        return sessionData;
    }
}



