package com.example.vitanovabackend.DAO.Controller;


import com.example.vitanovabackend.Service.CartService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Cart")
@MultipartConfig
@CrossOrigin
public class CartController {

    CartService cartService;
    @PostMapping("/{idUser}/")
    public ResponseEntity<String> addProductToCart( @PathVariable("idUser") Long idUser,
                                                    @RequestParam("idPr") Long idPr,
                                                     @RequestParam("quantity") Long quantity) {

        cartService.addProductToCart(idUser, idPr, quantity);

        return new ResponseEntity<>("Product added to cart successfully", HttpStatus.OK);
    }
}
