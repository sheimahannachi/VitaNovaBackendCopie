package com.example.vitanovabackend.DAO.Repositories;

import com.example.vitanovabackend.DAO.Entities.Cart;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserIdUser(Long userId);
}
