package com.example.vitanovabackend.Service;
import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import com.example.vitanovabackend.DAO.Repositories.ExerciseRepository;
import com.example.vitanovabackend.DAO.Repositories.WorkoutProgramRepository;
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


public class Workout implements Iworkout {
    WorkoutProgramRepository workoutProgramRepository;
    ExerciseRepository exerciseRepository;
    public static String uploadDirectory= "C:/xampp/php/htdocs/uploads";

    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException {
        String fileName = saveFile(file, uploadDirectory);
        workoutProgram.setImage(fileName);
        return workoutProgramRepository.save(workoutProgram);
    }


    @Override
    public WorkoutProgram UpdatePlan(WorkoutProgram workoutProgram,MultipartFile file) throws IOException  {
        String fileName = saveFile(file, uploadDirectory);
        workoutProgram.setImage(fileName);
        return workoutProgramRepository.save(workoutProgram);
    }
    public WorkoutProgram ArchiverPlan(long id){
        WorkoutProgram workoutProgram=workoutProgramRepository.findById(id).get();
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
    public Exercise UpdateExercise(Exercise exercise,MultipartFile file) throws IOException {
        String fileName = saveFile(file, uploadDirectory);
        exercise.setPicture(fileName);
        return exerciseRepository.save(exercise);
    }
    public Exercise ArchiverExercise(long id){
        Exercise exercise=exerciseRepository.findById(id).get();
        exercise.setArchived(true);
        return exerciseRepository.save(exercise);
    }
    @Override
    public void DeleteExercise(Exercise exercise) {
        exerciseRepository.delete(exercise);
    }

    @Override
    public List<Exercise> GetExercise() {

        return exerciseRepository.findActiveExercises();
    }


}


