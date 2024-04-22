package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.Cart;
import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.CartRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.Service.CartService;
import com.google.zxing.NotFoundException;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createUserCart(@PathVariable long userId) {
        cartService.createUserCart(userId);
        return ResponseEntity.ok("Cart created and associated with user successfully!");
    }
    @GetMapping("/{userId}/cart/products")
    public ResponseEntity<List<Commandeline>> getProductsInCart(@PathVariable Long userId) {
        try {
            List<Commandeline> productsInCart = cartService.getProductsInCart(userId);
            return ResponseEntity.ok(productsInCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
