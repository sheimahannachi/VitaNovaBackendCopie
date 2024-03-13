package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     List<User> findAllByFirstName(String firstName);
     List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
     List<User> findAllByLastName(String lastName);

    User findByEmail(String email);
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}