package com.example.vitanovabackend.DAO.Service;

import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ProductService implements ProductIService {

    private final ProductRepository productRepository;

    public static String uploadDirectory = "C:/xampp/htdocs/aziz/";

    @PostMapping("/addProduct")
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        try {
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            Path filePath = Paths.get(uploadDirectory, fileName);
            Files.write(filePath, file.getBytes());
            // Construire l'URL de l'image
            String imageUrl = fileName;
            // Définir l'URL de l'image sur l'objet Product
            product.setPicturePr(imageUrl);
            product.setArchivePr(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    public Product updateProduct(Long idPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile newImage) {
        try {

            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            String originalFilename = newImage.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            Path newFilePath = Paths.get(uploadDirectory, fileName);
            Files.write(newFilePath, newImage.getBytes());

            Product pr = productRepository.findById(idPr).orElseThrow(() -> new RuntimeException("Produit introuvable"));
            pr.setNamePr(updatedProduct.getNamePr());
            pr.setCategoriePr(updatedProduct.getCategoriePr());
            pr.setPricePr(updatedProduct.getPricePr());
            // Mise à jour du nom de l'image avec le nouveau nom généré
            pr.setPicturePr(fileName);
            return productRepository.save(pr);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de manière appropriée (par exemple, journalisez-la)
            throw new RuntimeException("Erreur lors de la mise à jour du produit", e);
        }
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long idPr) {
        return productRepository.findById(idPr).get();
    }
    @Override
    public ResponseEntity<String> archiverProduct(Long idPr) {
        Optional<Product> optionalProduct = productRepository.findById(idPr);
        if (optionalProduct.isPresent()) {
            Product produit = optionalProduct.get();
            produit.setArchivePr(true); // Met à jour la valeur d'archivePr à true
            productRepository.save(produit);
            return ResponseEntity.ok().body("Le produit a été archivé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<Product> searchProductsByName(String searchTerm) {
        return productRepository.findByNamePrContaining(searchTerm);
    }

}
