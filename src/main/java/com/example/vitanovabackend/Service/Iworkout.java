package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Exercise;
import com.example.vitanovabackend.DAO.Entities.WorkoutProgram;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface Iworkout {
    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException;
    public WorkoutProgram UpdatePlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException ;
    public void DeletePlan(WorkoutProgram workoutProgram);
    public List<WorkoutProgram> GetPlan();
    public Exercise addExercise (Exercise exercise, MultipartFile imageFile) throws IOException;
    public Exercise UpdateExercise (Exercise exercise,MultipartFile file) throws IOException;
    public void DeleteExercise (Exercise exercise);
    public List<Exercise> GetExercise ();
    public Exercise ArchiverExercise(long id);
}
