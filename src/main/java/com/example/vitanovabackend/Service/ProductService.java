package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.LikeProduct;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.LikeProductRepository;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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


    private final LikeProductRepository likeProductRepository;
    private UserRepository userRepository;

    private final ProductRepository productRepository;

    public static String uploadDirectory = "C:/xampp/htdocs/aziz/";


   /* public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file ) {
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
*/


    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file ) {
        try {

            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            uploadImage(file, fileName);

            product.setPicturePr(fileName);
            product.setArchivePr(false);


        } catch (IOException e) {


            e.printStackTrace();
        }
        // Sauvegarde du produit dans la base de données
        Product savedProduct = productRepository.save(product);
        // Renvoi du produit sauvegardé
        return savedProduct;
    }


/*
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
    }*/
public Product updateProduct(Long idPr, @ModelAttribute Product updatedProduct, @RequestParam("image") MultipartFile file) {
    try {
        Product pr = productRepository.findById(idPr)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        // Vérifier si le fichier est vide avant de le traiter
        if (!file.isEmpty()) {
            // Générer un nom de fichier unique pour l'image
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
            // Téléverser la nouvelle image sur le serveur FTP
            uploadImage(file, fileName);
            // Mettre à jour le nom de l'image dans le produit avec le nouveau nom généré
           // updatedProduct.setPicturePr(fileName); // Utilise le nouveau nom de fichier généré
            pr.setPicturePr(fileName);
        }

        // Récupérer le produit à mettre à jour depuis la base de données


        // Mettre à jour les attributs du produit avec les nouvelles valeurs
        pr.setNamePr(updatedProduct.getNamePr());
        pr.setCategoriePr(updatedProduct.getCategoriePr());
        pr.setPricePr(updatedProduct.getPricePr());
        pr.setQuantityPr(updatedProduct.getQuantityPr());
        pr.setDescriptionPr(updatedProduct.getDescriptionPr());
        pr.setStatusPr(updatedProduct.getStatusPr());

        // Sauvegarder et retourner le produit mis à jour
        return productRepository.save(pr);
    } catch (IOException e) {
        // Gérer l'erreur de manière appropriée (par exemple, journaliser-la)
        e.printStackTrace();
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


    public void uploadImage(MultipartFile file, String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("192.168.174.134", 21);
            ftpClient.login("aziz", "aziz123");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            InputStream inputStream = file.getInputStream();


            boolean done = ftpClient.storeFile(fileName, inputStream);
            if (done) {
                System.out.println("File uploaded successfully.");

            } else {
                System.out.println("Failed to upload file.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred during file upload: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.out.println("Error occurred while disconnecting FTP client: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void addLike(/*Long idUser,*/ Long idPr) {
        // Recherche de l'utilisateur et du produit correspondants
       // User user = userRepository.findById(idUser).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + idUser));
        Product product = productRepository.findById(idPr).orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + idPr));

        // Création du like pour le produit
        LikeProduct likeProduct = new LikeProduct();
        //likeProduct.setUser(user);
        likeProduct.setProduct(product);
        likeProductRepository.save(likeProduct);

        // Incrémentation du nombre de likes du produit
        product.setLikeCount(product.getLikeCount() + 1);
        productRepository.save(product);
    }
        public List<Product> getProductsSortedByLikes () {
            return productRepository.findAllByOrderByLikeCountDesc();
        }

        public void incrementLikeCount (Long productId){
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
            product.setLikeCount(product.getLikeCount() + 1);
            productRepository.save(product);
        }

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

    @Override
    public void updateProductImage(Long productId, String imagePath) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID : " + productId));
        product.setPicturePr(imagePath);
        productRepository.save(product);
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