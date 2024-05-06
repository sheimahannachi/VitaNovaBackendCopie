package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.Service.ProductIService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Product")
@MultipartConfig
@CrossOrigin(origins="*")
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
   /* @GetMapping("/filtered")
    public     List<Product> findByCategoriePrAndStatusPrAndPricePrLessThanEqual(String categoriePr, String statusPr, float pricePr){
        return productIService.filterProductsByCategoryAndStatusAndPriceRange(categoriePr,statusPr , pricePr);
    }*/

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
    public ResponseEntity<Void> addLike(@RequestParam("idUser") Long idUser, @PathVariable("idPr") Long idPr) {
       if( productIService.addLike(idUser,idPr))
        return ResponseEntity.ok().build();
       else return ResponseEntity.badRequest().build();
    }


    @PostMapping("/addProductToCart/")
    public void addProductToCart(@RequestParam Long idPr, @RequestParam Long idUser) {
        try {
            productIService.addProductToCart(idPr, idUser);
        } catch (Exception e) {
        }
    }

    @DeleteMapping("/{userId}/cart/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            productIService.deleteProductFromCart( userId,productId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{productId}/generate-qrcode")
    public ResponseEntity<String> generateQRCodeForProduct(@PathVariable Long productId) {
        try {
            productIService.generateQRCodeForProduct(productId);
            return ResponseEntity.ok("QR Code generated successfully for product ID: " + productId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating QR Code for product ID " + productId + ": " + e.getMessage());
        }
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<Map<String, Object>> getInvoiceDetails( @PathVariable Long userId) {
        Map<String, Object> invoiceData = productIService.getInvoiceData( userId);
        return ResponseEntity.ok(invoiceData);
    }
}