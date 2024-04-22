package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.Service.CartService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Cart")
@MultipartConfig
@CrossOrigin
public class CartController {

    CartService cartService;



    @GetMapping("/commandelines/{idCart}")
    public List<Commandeline> getAllCommandelinesInCart(@PathVariable Long idCart) {
        return cartService.getAllCommandelinesInCart(idCart);
    }
    @GetMapping("/count/{cartId}")
    public ResponseEntity<Integer> getNumberOfCommandelinesInCart(@PathVariable Long cartId) {
        // Assuming you have a method in your service to get the count of command lines in the cart
        int numberOfCommandelines = cartService.getNumberOfCommandelinesInCart(cartId);
        return ResponseEntity.ok(numberOfCommandelines);
    }
}
