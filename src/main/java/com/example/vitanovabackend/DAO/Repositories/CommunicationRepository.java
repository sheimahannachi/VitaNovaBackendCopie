package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Communication;
import com.example.vitanovabackend.DAO.Entities.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication,Long> {


    @Query("SELECT c FROM Communication c WHERE (c.sender.idUser = :id1 OR c.reciever.idUser = :id1) AND (c.sender.idUser = :id2 OR c.reciever.idUser = :id2)"+
            "order by c.id desc")
    Page<Communication> findBySenderAndOrReciever(@Param("id1") long id1, @Param("id2") long id2,Pageable pageable);

    Page<Communication> findAllByCommunityOrderByIdDesc(Community community, Pageable pageable);

    List<Communication>findByCommunity(Community community);

    long countByCommunity(Community community);

    // select c from Communication c join Community q on q.id=c.comunity.id where q.id=?id
    List<Communication>getCommunicationByCommunityIdAndSeenIsFalse(long comunityId);
    List<Communication>getCommunicationBySenderIdUserAndRecieverIdUserAndSeenIsFalse(long senderId,long revieverId);
}
