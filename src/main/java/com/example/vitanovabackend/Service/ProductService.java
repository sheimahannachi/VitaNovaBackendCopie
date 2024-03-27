package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ProductService implements ProductIService {

    private final ProductRepository productRepository;

    public static String uploadDirectory = "C:/xampp/htdocs/aziz/";
    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file ) {
        try {
            // Récupération du chemin du répertoire de téléchargement
            Path directoryPath = Paths.get(uploadDirectory);
            // Vérification de l'existence du répertoire, s'il n'existe pas, il est créé
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            // Récupération du nom de fichier d'origine
            String originalFilename = file.getOriginalFilename();
            // Génération d'un nom de fichier unique en utilisant UUID pour éviter les conflits de noms
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            // Définition du chemin complet du fichier à télécharger
            Path filePath = Paths.get(uploadDirectory, fileName);
            // Écriture du contenu du fichier dans le chemin spécifié
            Files.write(filePath, file.getBytes());
            // Construction de l'URL de l'image en utilisant le nom du fichier
            String imageUrl = fileName;
            // Définition de l'URL de l'image sur l'objet Product
            product.setPicturePr(imageUrl);
            // Définition de l'archivePr sur false, indiquant que le produit n'est pas archivé
            product.setArchivePr(false);
        } catch (IOException e) {
            // Gestion des erreurs d'entrée/sortie en cas de problème lors de l'écriture du fichier
            e.printStackTrace();
        }
        // Sauvegarde du produit dans la base de données
        Product savedProduct = productRepository.save(product);
        // Renvoi du produit sauvegardé
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
            pr.setQuantityPr(updatedProduct.getQuantityPr());
            pr.setDescriptionPr(updatedProduct.getDescriptionPr());
            pr.setStatusPr(updatedProduct.getStatusPr());

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

  /*  @Override
    public  List<Product> findByCategoriePrAndPricePrAndStatusPr(String categoriePr, float pricePr, String statusPr){
        return productRepository.findByCategoriePrAndPricePrAndStatusPr(categoriePr,pricePr,statusPr);
    }*/


    @Override
    public List<Product> filterProducts(String categoriePr, Float pricePr) {
        // Récupérer tous les produits de la base de données
        List<Product> allProducts = productRepository.findAll();

        // Initialiser la liste des produits filtrés avec tous les produits
        List<Product> filteredProducts = new ArrayList<>(allProducts);

        // Filtrer par catégorie si la catégorie est spécifiée
        if (categoriePr != null && !categoriePr.isEmpty()) {
            // Filtrer les produits qui correspondent à la catégorie spécifiée
            filteredProducts = filteredProducts.stream()
                    .filter(product -> categoriePr.equals(product.getCategoriePr()))
                    .collect(Collectors.toList());
        }

        // Filtrer par prix si le prix est spécifié
        if (pricePr != null) {
            // Filtrer les produits dont le prix est supérieur ou égal au prix spécifié
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getPricePr() >= pricePr)
                    .collect(Collectors.toList());
        }

        // Retourner la liste des produits filtrés
        return filteredProducts;
    }



}




/*public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file ,Long idOrder) {

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

                // Enregistrer le produit dans la base de données
                Product savedProduct = productRepository.save(product);

                // Si orderId est fourni, ajouter le produit à la commande
                if (idOrder != null) {
                    orderServcie.addProductToOrder(idOrder, savedProduct.getIdPr());
                }

                return savedProduct;
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur de manière appropriée
                throw new RuntimeException("Erreur lors de l'ajout du produit", e);
            }
        }*/