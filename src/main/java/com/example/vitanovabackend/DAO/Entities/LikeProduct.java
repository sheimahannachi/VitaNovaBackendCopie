package com.example.vitanovabackend.DAO.Entities;


import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(LikeProduct.LikeProductId.class)
public class LikeProduct {

    @Id
    private Long userId;

    @Id
    private Long productId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    // Other fields and relationships...

    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class LikeProductId implements Serializable {
        private Long userId;
        private Long productId;
    }
}
