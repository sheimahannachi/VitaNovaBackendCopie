package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.IPAdresses;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpAddressesRepository extends JpaRepository<IPAdresses,Long> {


    Boolean existsByValue(String ipaddress);


    IPAdresses findByUserAndValue(User user, String value);

}
