package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Challenges;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenges,Long> {

    Challenges findByActiveTrueAndCommunityId(long is);

    List<Challenges> findAllByActiveTrue();

    @Query("select u from User u where u.community.id=:id")
    List<User> communityMembers(@Param("id")long id);

    Page<Challenges> findAll(Pageable pageable);


}
