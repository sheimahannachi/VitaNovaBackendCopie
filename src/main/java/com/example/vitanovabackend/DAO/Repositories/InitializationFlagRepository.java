package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.InitializationFlag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InitializationFlagRepository extends JpaRepository<InitializationFlag, Long> {
}
