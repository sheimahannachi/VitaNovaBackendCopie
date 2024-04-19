package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.Service.CartIService;
import com.example.vitanovabackend.Service.CartService;
import com.example.vitanovabackend.Service.ProductIService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Product")
@MultipartConfig
@CrossOrigin(origins="*")
public class ProductController {
    ProductIService productIService;
    CartIService cartIService;

    @PostMapping("addProduct")
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        return productIService.addProduct(product, file);
    }

    @PutMapping("updateProduct/{idPr}")

    public Product updateProduct(@PathVariable Long idPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile newImage) {
        return productIService.updateProduct(idPr, updatedProduct, newImage);
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts() {
        return productIService.getProducts();
    }


    @GetMapping("getProductById/{idPr}")
    public Product getProductById(@PathVariable Long idPr) {

        return productIService.getProductById(idPr);
    }
    @PutMapping("/{idPr}")
    public ResponseEntity<String> archiverProduct(@PathVariable Long idPr) {
        return productIService.archiverProduct(idPr);
    }
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam String term) {
        return productIService.searchProductsByName(term);
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(@RequestParam(required = false) String categoriePr,
                                        @RequestParam(required = false) Float pricePr ){
        // Vérifier et appliquer les filtres
        if (categoriePr != null && !categoriePr.isEmpty() &&
                (categoriePr.equals("NUTRITION") || categoriePr.equals("Fitness_Equipement") || categoriePr.equals("Mentall_wellbeing")) &&
                pricePr != null) {
            return productIService.filterProducts(categoriePr, pricePr);

        } else {
            // Retourner une liste vide si les paramètres de filtrage ne sont pas valides
            return List.of();
        }
    }

    @PostMapping("/addLike/{idPr}")
    public ResponseEntity<Void> addLike(/*@RequestParam("idUser") Long idUser,*/ @PathVariable("idPr") Long idPr) {
        productIService.addLike(idPr);
        return ResponseEntity.ok().build();
    }


    /////////////////////////

    @PostMapping("/addProductToCart/")
    public ResponseEntity<String> addProductToCart(@RequestParam Long idPr, @RequestParam Long idUser) {
        try {
            productIService.addProductToCart(idPr, idUser);
            return ResponseEntity.ok("Product successfully added to cart.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add product to cart: " + e.getMessage());
        }
    }

    @DeleteMapping("/cart/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            productIService.deleteProductFromCommandelines(productId, cartId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}