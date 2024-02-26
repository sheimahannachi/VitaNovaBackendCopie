package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import com.example.vitanovabackend.Service.Iworkout;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("RestController")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkoutController {
    Iworkout iWorkout;
    @PostMapping("addPlan")
    public WorkoutProgram addPlan(@ModelAttribute WorkoutProgram workoutProgram, @RequestParam("image") MultipartFile file) throws IOException {
        return iWorkout.addPlan(workoutProgram,file);
    }


    @PutMapping("UpdatePlan")
    public WorkoutProgram UpdatePlan(@ModelAttribute WorkoutProgram workoutProgram,@RequestParam("image") MultipartFile file) throws IOException {
        return iWorkout.UpdatePlan(workoutProgram,file);
    }

    @DeleteMapping("DeletePlan")
    public void DeletePlan(@RequestBody WorkoutProgram workoutProgram) {
        iWorkout.DeletePlan(workoutProgram);
    }

    @GetMapping("GetPlan")
    public List<WorkoutProgram> GetPlan() {
        return iWorkout.GetPlan();
    }
    @PostMapping("addExercise")
    public Exercise addExercise(@ModelAttribute  Exercise exercise, @RequestParam("file") MultipartFile imageFile) throws IOException {
        return iWorkout.addExercise(exercise,imageFile);

    }

    @PutMapping("UpdateExercise")
    public Exercise UpdateExercise(@ModelAttribute Exercise exercise,@RequestParam("file") MultipartFile file) throws IOException {
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
    public List<Exercise> GetExercise() {
        return iWorkout.GetExercise();
    }

}
