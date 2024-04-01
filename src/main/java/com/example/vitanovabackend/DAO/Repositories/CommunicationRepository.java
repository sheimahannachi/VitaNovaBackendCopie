package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication,Long> {


    List<Communication> findBySenderAndReciever(User sender, User reciever);

    Page<Communication> findAllByCommunityOrderByIdDesc(Community community, Pageable pageable);

    List<Communication>findByCommunity(Community community);

    long countByCommunity(Community community);
}
