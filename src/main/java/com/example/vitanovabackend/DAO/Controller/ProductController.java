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
}