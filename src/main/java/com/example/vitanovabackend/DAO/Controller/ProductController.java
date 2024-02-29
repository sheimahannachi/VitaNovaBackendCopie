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
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        return productIService.addProduct(product, file);
    }

    @PutMapping("updateProduct/{IdPr}")

    public Product updateProduct(@PathVariable Long IdPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile newImage) {
        return productIService.updateProduct(IdPr, updatedProduct, newImage);
    }

    @GetMapping("getProducts")
    public List<Product> getProducts() {
        return productIService.getProducts();
    }


    @GetMapping("getProductById/{IdPr}")
    public Product getProductById(@PathVariable Long IdPr) {

        return productIService.getProductById(IdPr);
    }
    @PutMapping("/{IdPr}")
    public ResponseEntity<String> archiverProduct(@PathVariable Long IdPr) {
        return productIService.archiverProduct(IdPr);
    }
}
