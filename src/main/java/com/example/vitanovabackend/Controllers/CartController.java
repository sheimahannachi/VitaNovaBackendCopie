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
@CrossOrigin(origins="*")
public class CartController {

    CartService cartService;
    @GetMapping("/{idCart}/commandelines")
    public List<Commandeline> getAllCommandelinesInCart(@PathVariable Long idCart) {
        return cartService.getAllCommandelinesInCart(idCart);
    }
    @GetMapping("/{cartId}/count")
    public int getNumberOfCommandelinesInCart(@PathVariable Long cartId) {
        // Assuming you have a method in your service to get the count of command lines in the cart
        return cartService.getNumberOfCommandelinesInCart(cartId);
    }
/*
    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> updateProductQuantityInCart(@PathVariable Long cartId,
                                                         @PathVariable Long productId,
                                                         @RequestParam("newQuantity") int newQuantity) {
        try {
            cartService.updateProductQuantityInCart(cartId, productId, newQuantity);
            return ResponseEntity.ok("La quantité du produit dans le panier a été mise à jour avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/


}
