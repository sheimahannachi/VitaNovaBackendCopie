package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Commandeline;

import java.util.List;

public interface CartIService {


    public List<Commandeline> getAllCommandelinesInCart(Long idCart);
    public int getNumberOfCommandelinesInCart(Long cartId);
     void createUserCart(long userId) ;
}