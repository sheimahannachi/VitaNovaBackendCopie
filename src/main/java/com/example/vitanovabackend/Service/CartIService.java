package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Commandeline;

import java.util.List;

public interface CartIService {
    public void addProductToCart(Long idUser, Long idPr, Long quantity);
    public List<Commandeline> getAllCommandelinesInCart(Long idCart);
    public int getNumberOfCommandelinesInCart(Long cartId);
}
