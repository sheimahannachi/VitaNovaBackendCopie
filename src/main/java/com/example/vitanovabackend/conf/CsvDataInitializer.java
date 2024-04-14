package com.example.vitanovabackend.conf;

import com.example.vitanovabackend.DAO.Repositories.ExerciseRepository;
import com.example.vitanovabackend.Service.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvDataInitializer implements ApplicationRunner {
    private final Workout exerciseCsvService;
    private final ExerciseRepository exerciseRepository;
    @Autowired
    public CsvDataInitializer(Workout exerciseCsvService,ExerciseRepository exerciseRepository) {
        this.exerciseCsvService = exerciseCsvService;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (exerciseRepository.count() == 0) {
            exerciseCsvService.importExercisesFromCsv();
        } else {
            System.out.println("Exercise Database already contains data. Skipping data import.");
        }
    }
}
