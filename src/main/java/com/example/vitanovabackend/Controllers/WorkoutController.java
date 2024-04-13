package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.UserRating;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import com.example.vitanovabackend.Service.Iworkout;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("RestController")
@CrossOrigin(origins = "*")
@MultipartConfig
public class WorkoutController {
    Iworkout iWorkout;


    @PostMapping("addPlan")
    public WorkoutProgram addPlan(@ModelAttribute WorkoutProgram workoutProgram, @RequestParam("fileImage") MultipartFile file, @RequestParam("selectedExerciseIds") String[] selectedExerciseIds) throws IOException {
        return iWorkout.addPlan(workoutProgram, file, selectedExerciseIds);
    }



    @PutMapping("UpdatePlan")
    public WorkoutProgram UpdatePlan(@ModelAttribute WorkoutProgram workoutProgram,@RequestParam("image") MultipartFile file) throws IOException {
        return iWorkout.UpdatePlan(workoutProgram,file);
    }
    @DeleteMapping("ArchiverPlan/{idplan}")
    public WorkoutProgram ArchiverPlan(@PathVariable("idplan") long id){
        return iWorkout.ArchiverPlan(id);
    }
    @DeleteMapping("DeletePlan")
    public void DeletePlan(@RequestBody WorkoutProgram workoutProgram) {
        iWorkout.DeletePlan(workoutProgram);
    }

    @GetMapping("GetPlan")
    public Page<WorkoutProgram> GetPlan(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        return iWorkout.GetPlan(page, size);
    }
    @GetMapping("getPlan/{id}")
    public WorkoutProgram GetPlanById(@PathVariable("id") long id){
        return iWorkout.GetPlanById(id);
    }

    @PostMapping("addExercise")
    public ResponseEntity<Exercise> addExercise(@ModelAttribute  Exercise exercise, @RequestParam("file") MultipartFile imageFile) throws IOException {
        try {

            Exercise savedExercise = iWorkout.addExercise(exercise, imageFile);
            return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping("UpdateExercise/{id}")
    public Exercise UpdateExercise(@PathVariable("id") long id,@ModelAttribute Exercise exercise,@RequestParam("file") MultipartFile file) throws IOException {
        return iWorkout.UpdateExercise(exercise,file);
    }

    @DeleteMapping("DeleteExercise")
    public void DeleteExercise(@RequestBody Exercise exercise) {

        iWorkout.DeleteExercise(exercise);
    }
    @DeleteMapping("ArchiverExercise/{idex}")
    public Exercise ArchiverExercise(@PathVariable("idex") long id){
        return iWorkout.ArchiverExercise(id);
    }

    @GetMapping("GetExercise")
    public Page<Exercise> GetExercise(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        return iWorkout.GetExercise(page,size);
    }
    @GetMapping("GetActiveExercise")
    public Page<Exercise> GetActiveExercise(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        return iWorkout.GetActiveExercise(page,size);
    }
   /* @PostMapping("rateExercise/{id}/{rate}")
    public Exercise rateExercise(@PathVariable("id") long id,@PathVariable("rate") int rate){
        return iWorkout.rateExercise(id,rate);
    }*/
    @PostMapping("saveUserExerciseRating/{idEx}")
    public UserRating saveUserExerciseRating(@RequestBody UserRating userExerciseRating,@PathVariable("idEx") long idExercise){
        return iWorkout.saveUserExerciseRating(userExerciseRating,idExercise);
    }
@GetMapping("getExerciseById/{exerciseId}")
public Exercise getExerciseById(@PathVariable("exerciseId") long id){
        return iWorkout.getExerciseById(id);
}
@GetMapping("Rating/{exerciseId}")
    public double calculateAverageRating(@PathVariable("exerciseId") long exerciseId){
        return iWorkout.calculateAverageRating(exerciseId);
    }
   /* @GetMapping("searchEx")
    public  Page<Exercise> searchExercises(@RequestParam(required = false) String bodyPart, @RequestParam(required = false) String searchText,@RequestParam int page,@RequestParam int size)
    {
        return iWorkout.searchExercises(bodyPart,searchText,page,size);
    }*/
    @GetMapping("/filtered")
    public ResponseEntity<Page<Exercise>> getFilteredExercises(
            @RequestParam( defaultValue = "0") int page,
            @RequestParam( defaultValue = "10") int size,
            @RequestParam List<String> bodyPart) {
        Page<Exercise> exercises = iWorkout.getFilteredExercises(page, size, bodyPart);
        return ResponseEntity.ok().body(exercises);
    }
    @GetMapping("sorted-by-rating")
    public Page<Exercise> getExercisesSortedByRating(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

            Page<Exercise> exercises = iWorkout.findExercisesOrderByAverageRating(page, size);
            return exercises;}

}

