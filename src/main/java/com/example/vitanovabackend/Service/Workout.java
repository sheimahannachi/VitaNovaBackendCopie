package com.example.vitanovabackend.Service;
import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Entities.UserRating;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import com.example.vitanovabackend.DAO.Repositories.ExerciseRepository;
import com.example.vitanovabackend.DAO.Repositories.UserExerciseRatingRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.DAO.Repositories.WorkoutProgramRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j


public class Workout implements Iworkout {
    WorkoutProgramRepository workoutProgramRepository;
    ExerciseRepository exerciseRepository;
    UserExerciseRatingRepository userExerciseRatingRepository;
    UserRepository userRepository;
    public static String uploadDirectory = "C:/xampp/php/htdocs/uploads";

    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file, String bodypart, String intensity, String typeEx) throws IOException {

        List<Exercise> bodyPartExercises = exerciseRepository.getExercisesByBodypartAndIntensity(bodypart, intensity);
        // Fetch exercises based on type of exercise and intensity
        List<Exercise> typeExExercises = exerciseRepository.getExercisesByTypeExAndIntensity(typeEx, intensity);
        // Merge the results
        List<Exercise> mergedExercises = mergeLists(bodyPartExercises, typeExExercises);

        // Associate fetched exercises with the workout program
        workoutProgram.setExercises(mergedExercises);
        String fileName = saveFile(file, uploadDirectory);
        workoutProgram.setImage(fileName);
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
    public List<WorkoutProgram> GetPlan() {

        return workoutProgramRepository.findAll();
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
    public List<Exercise> GetExercise() {

        return exerciseRepository.findAll();
    }

    public List<Exercise> GetActiveExercise() {

        return exerciseRepository.findActiveExercises();
    }

    /*public Exercise rateExercise(long id,int rate){
        Exercise exercise=exerciseRepository.findById(id).get();
        exercise.setRate(rate);
        return exerciseRepository.save(exercise);
    }
*/
    public UserRating saveUserExerciseRating(UserRating userExerciseRating, long idExercise) {
        Exercise exercise = exerciseRepository.findById(idExercise).orElse(null);
        if (exercise != null) {
            userExerciseRating.setExercise(exercise);
            return userExerciseRatingRepository.save(userExerciseRating);
        } else {
            // Gérer l'erreur si l'exercice n'est pas trouvé
            return null;

        }
    }
}


