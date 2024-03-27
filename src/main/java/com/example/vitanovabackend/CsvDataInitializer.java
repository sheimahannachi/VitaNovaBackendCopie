package com.example.vitanovabackend;

import com.example.vitanovabackend.DAO.Entities.InitializationFlag;
import com.example.vitanovabackend.DAO.Repositories.InitializationFlagRepository;
import com.example.vitanovabackend.Service.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class CsvDataInitializer implements ApplicationRunner {
    private final Workout exerciseCsvService;
    private final InitializationFlagRepository flagRepository;

    @Autowired
    public CsvDataInitializer(Workout exerciseCsvService, InitializationFlagRepository flagRepository) {
        this.exerciseCsvService = exerciseCsvService;
        this.flagRepository = flagRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!flagRepository.existsById(1L)) {
            // Import data from CSV file
            exerciseCsvService.importExercisesFromCsv();

            // Set the initialization flag
            InitializationFlag flag = new InitializationFlag();
            flag.setInitialized(true);
            flagRepository.save(flag);
        }
    }
}
