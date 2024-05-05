package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Community;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {

    Page<Community> findByCommunityName(String name, Pageable pageable);

    Page<Community> findAll(Pageable pageable);

    @Query("select c from Community  c left join c.challenges ch where c.status=true group by c.id order by count (ch) desc ")
    Page<Community> findAllOrderByCountChallenges(Pageable pageable);


    List<Community> findAllByStatusIsTrue();

    @Query("select u from Community c join User u on u.community.id=c.id   where c.id=:id order by  u.comunityActivity desc ")
    List<User>topThree(@Param("id")long communityId, Pageable pageable);

    @Query("select c.membres from Community c where c.id=:id")
    List<User>getCommunityMembers(@Param("id")long communityId);


    @Query("select c from Community  c join User  u on u.community.id = c.id where u.idUser=:userId")
    Optional<Community> getCommunityByUserId(@Param("userId")long userId);


}
