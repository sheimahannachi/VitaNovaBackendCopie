package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {

    Page<Community> findByCommunityName(String name, Pageable pageable);

    Page<Community> findAll(Pageable pageable);

    @Query("select c from Community  c join c.challenges ch group by c.id order by count (ch)")
    Page<Community> findAllOrderByCountChallenges(Pageable pageable);


    List<Community> findAllByStatusIsTrue();


}
