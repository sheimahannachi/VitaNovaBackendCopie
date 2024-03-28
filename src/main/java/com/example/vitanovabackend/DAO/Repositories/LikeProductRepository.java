package com.example.vitanovabackend.DAO.Repositories;


import com.example.vitanovabackend.DAO.Entities.Cart;
import com.example.vitanovabackend.DAO.Entities.LikeProduct;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeProductRepository extends JpaRepository<LikeProduct,Long> {
    boolean existsByUserAndProduct(User user, Product product);
}
