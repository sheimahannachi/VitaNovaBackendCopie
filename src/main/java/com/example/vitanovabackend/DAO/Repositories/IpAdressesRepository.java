package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.IPAdresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAdressesRepository extends JpaRepository<IPAdresses,Long> {
}
