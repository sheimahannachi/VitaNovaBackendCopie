package com.example.vitanovabackend.Service;


import com.example.vitanovabackend.DAO.Entities.LikeProduct;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.LikeProductRepository;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class LikeProductService  implements  LikeProductIService{
/*
    private final LikeProductRepository likeProductRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;


    public void addLike(Long idUser, Long idPr) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + idUser));
        Product product = productRepository.findById(idPr).orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + idPr));
        LikeProduct likeProduct = new LikeProduct();
        likeProduct.setUser(user);
        likeProduct.setProduct(product);
        likeProductRepository.save(likeProduct);
    }*/
}
