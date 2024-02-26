package com.example.vitanovabackend.DAO.Controller;


import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Service.ProductIService;
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
@CrossOrigin
public class ProductController {
    ProductIService productIService;

    @PostMapping("addProduct")
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file){
        return productIService.addProduct(product,file);
    }
    @PutMapping("updateProduct/{IdPr}")

    public Product updateProduct(@PathVariable Long IdPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile newImage) {
        return productIService.updateProduct(IdPr,updatedProduct,newImage);
    }
    @GetMapping("getProducts")
    public List<Product> getProducts () {
        return productIService.getProducts();
    }


    @DeleteMapping("/{IdPr}")
    public ResponseEntity<String> archiverProduit(@PathVariable Long IdPr) {
        try {
            productIService.archiverProduit(IdPr);
            return ResponseEntity.ok("Le produit a été archivé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de l'archivage du produit : " + e.getMessage());
        }
    }
}
