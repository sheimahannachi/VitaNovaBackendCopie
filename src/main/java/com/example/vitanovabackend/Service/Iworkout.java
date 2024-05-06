package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Iworkout {
    public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file,String[] selectedExerciseIds) throws IOException;
   // public WorkoutProgram addPlan(WorkoutProgram workoutProgram, MultipartFile file,String bodypart,String intensity,String typeEx)throws IOException;
    public WorkoutProgram UpdatePlan(WorkoutProgram workoutProgram, MultipartFile file) throws IOException ;
    public WorkoutProgram ArchiverPlan(long id);
    public void DeletePlan(WorkoutProgram workoutProgram);
    public Page<WorkoutProgram> GetPlan(int page,int size);
    public WorkoutProgram GetPlanById(long id);
    public Exercise addExercise (Exercise exercise, MultipartFile imageFile) throws IOException;
    public Exercise UpdateExercise (Exercise exercise,MultipartFile file) throws IOException;
    public void DeleteExercise (Exercise exercise);
    public Page<Exercise> GetExercise(int page,int size);
    public Exercise ArchiverExercise(long id);
    public Page<Exercise> GetActiveExercise(int page, int size);
    //public Exercise rateExercise(long id,int rate);
    public UserRating saveUserExerciseRating(UserRating userExerciseRating, long userId, long idExercise) ;
    public Exercise getExerciseById(long id);
    public double calculateAverageRating(long exerciseId);
 //   public  Page<Exercise> searchExercises(String bodyParts, String searchText,int page,int size);
 public Page<Exercise> getFilteredExercises(int page, int size, List<String> bodyPart);
 public Page<Exercise> findExercisesOrderByAverageRating(int page, int size);
 public WorkoutSession addWorkoutSession(WorkoutSession workoutSession,long id);
 public WorkoutSession addSession(WorkoutSession workoutSession, long id, Intensity intensity);
 public Map<String, List<Object[]>> getUserTrainingStatistics(long userId);
 public List<Object[]> getAllWorkoutSessionData(long id);
}
