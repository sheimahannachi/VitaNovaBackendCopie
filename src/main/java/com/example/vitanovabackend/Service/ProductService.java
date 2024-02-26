package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class ProductService implements ProductIService{

    private final ProductRepository productRepository;
    public static String uploadDirectory = System.getProperty("user.dir") + "src/main/java/com/example/vitanovabackend/DAO/uploads";

    @Override
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        try {
            // Vérifie si le répertoire existe, sinon le crée
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Génère un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // Chemin complet du fichier
            Path filePath = Paths.get(uploadDirectory, fileName);

            // Enregistre le fichier dans le répertoire spécifié
            Files.write(filePath, file.getBytes());

            // Définit le nom du fichier dans le produit
            product.setPicturePr(fileName);

            product.setArchivePr(true);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de manière appropriée (par exemple, journalisez-la)
        }

        // Retourne le produit avec le nom du fichier mis à jour
        return productRepository.save(product);
    }

    public Product updateProduct(Long IdPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile newImage) {
        try {
            // Vérifie si le répertoire existe, sinon le crée
            Path directoryPath = Paths.get(uploadDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Génère un nom de fichier unique pour la nouvelle image
            String originalFilename = newImage.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // Chemin complet du fichier pour la nouvelle image
            Path newFilePath = Paths.get(uploadDirectory, fileName);

            // Enregistre le fichier de la nouvelle image dans le répertoire spécifié
            Files.write(newFilePath, newImage.getBytes());

            // Obtient le produit à mettre à jour depuis la base de données
            Product pr = productRepository.findById(IdPr).orElseThrow(() -> new RuntimeException("Produit introuvable"));

            // Met à jour les autres attributs du produit avec les valeurs fournies dans updatedProduct
            pr.setNamePr(updatedProduct.getNamePr());
            pr.setCategoriePr(updatedProduct.getCategoriePr());
            pr.setPricePr(updatedProduct.getPricePr());
            // Mise à jour du nom de l'image avec le nouveau nom généré
            pr.setPicturePr(fileName);

            // Enregistre les modifications dans la base de données
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
    public void archiverProduit(Long idPr) {
        Optional<Product> optionalProduct = productRepository.findById(idPr);
        if (optionalProduct.isPresent()) {
            Product produit = optionalProduct.get();
            produit.setArchivePr(false); // Met à jour la valeur d'archivePr à 0
            productRepository.save(produit);
        }
    }
}
