package com.example.vitanovabackend.DAO.Repositories;


import com.example.vitanovabackend.DAO.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNamePrContaining(String searchTerm);
}
