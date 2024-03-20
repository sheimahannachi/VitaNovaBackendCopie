package com.example.vitanovabackend.DAO.Repositories;
import java.util.Optional;

import com.example.vitanovabackend.DAO.Entities.ERole;
import com.example.vitanovabackend.DAO.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
