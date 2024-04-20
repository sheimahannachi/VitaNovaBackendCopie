package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ProductService implements ProductIService {

    CartRepository cartRepository;
    private final LikeProductRepository likeProductRepository;
    private UserRepository userRepository;
    private CommandelineRepository commandelineRepository;
    private final ProductRepository productRepository;

    public static String uploadDirectory = "C:/xampp/htdocs/aziz/";


    public Product addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        try {
            // Récupération du nom de fichier d'origine de l'image
            String originalFilename = file.getOriginalFilename();

            // Génération d'un nom de fichier unique à l'aide d'UUID pour éviter les conflits de noms de fichiers
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // Téléchargement de l'image sur le serveur avec le nom de fichier généré
            uploadImage(file, fileName);

            // Attribution du nom de fichier généré à l'attribut 'picturePr' du produit
            product.setPicturePr(fileName);

            // Définition de l'attribut 'archivePr' du produit à 'false' car le nouveau produit ne doit pas être archivé par défaut
            product.setArchivePr(false);
        } catch (IOException e) {
            // Gestion des erreurs d'entrée/sortie lors du téléchargement de l'image
            e.printStackTrace();
        }

        // Sauvegarde du produit dans la base de données
        Product savedProduct = productRepository.save(product);

        // Renvoi du produit sauvegardé
        return savedProduct;
    }

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
        // Recherche du produit dans la base de données par son identifiant
        Optional<Product> optionalProduct = productRepository.findById(idPr);

        // Vérifier si le produit existe dans la base de données
        if (optionalProduct.isPresent()) {
            // Si le produit existe, le récupérer de l'Optional
            Product produit = optionalProduct.get();

            // Mettre à jour la valeur de l'attribut 'archivePr' du produit à true pour l'archiver
            produit.setArchivePr(true);

            // Sauvegarder le produit mis à jour dans la base de données
            productRepository.save(produit);

            // Renvoyer une réponse HTTP 200 (OK) avec un message de succès
            return ResponseEntity.ok().body("Le produit a été archivé avec succès.");
        } else {
            // Si le produit n'est pas trouvé, renvoyer une réponse HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public List<Product> searchProductsByName(String searchTerm) {
        return productRepository.findByNamePrContaining(searchTerm);
    }


    public void uploadImage(MultipartFile file, String fileName) throws IOException {
        // Création d'une instance FTPClient
        FTPClient ftpClient = new FTPClient();
        try {
            // Connexion au serveur FTP
            ftpClient.connect("192.168.174.134", 21);
            // Authentification avec un nom d'utilisateur et un mot de passe
            ftpClient.login("aziz", "aziz123");
            // Activation du mode passif pour éviter les problèmes de connexion
            ftpClient.enterLocalPassiveMode();
            // Définition du type de fichier comme binaire
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Récupération du flux d'entrée du fichier
            InputStream inputStream = file.getInputStream();

            // Stockage du fichier sur le serveur FTP avec le nom spécifié
            boolean done = ftpClient.storeFile(fileName, inputStream); // obtenir un flux d'entre à partir du fichier téléchargé
            if (done) {
                System.out.println("File uploaded successfully.");
            } else {
                System.out.println("Failed to upload file.");
            }
        } catch (IOException e) {
            // Gestion des exceptions en cas d'erreur lors du téléchargement du fichier
            System.out.println("Error occurred during file upload: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Déconnexion du client FTP dans une clause finally pour garantir qu'elle est toujours exécutée
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                // Gestion des exceptions lors de la déconnexion du client FTP
                System.out.println("Error occurred while disconnecting FTP client: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
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

    public void addProductToCart(Long idPr, Long idUser) throws RuntimeException {
        // Retrieve the user from the database based on userId
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the product from the database based on productId
        Product product = getProductById(idPr);

        // Check if the product exists
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // Retrieve the cart associated with the user
        Cart cart = user.getCart();

        // If user doesn't have a cart, create a new one and associate it
        if (cart == null) {
            cart = new Cart();
            cart = cartRepository.save(cart);
            user.setCart(cart); // Associate the cart with the user
        }

        // Check if the product already exists in the cart's command lines
        Commandeline existingCommandLine = cart.getCommandelineList().stream()
                .filter(commandLine -> commandLine.getProduct().getIdPr().equals(idPr))
                .findFirst()
                .orElse(null);

        // Calculate total price of the cart
        float totalPrice = calculateTotalPrice(cart);

        if (existingCommandLine != null) {
            // If the product already exists in the cart, update the existing command line
            float newQuantity = existingCommandLine.getQuantity() + 1; // Increment quantity by 1
            if (newQuantity <= product.getQuantityPr()) {
                existingCommandLine.setQuantity(newQuantity);
                existingCommandLine.setPrixOrder(existingCommandLine.getProduct().getPricePr() * newQuantity);
            } else {
                throw new RuntimeException("Product quantity exceeded for product ID: " + idPr);
            }
        } else {
            // If the product doesn't exist in the cart, create a new command line
            if (1 <= product.getQuantityPr()) { // Check if product has enough quantity
                Commandeline commandLine = new Commandeline();
                commandLine.setProduct(product);
                commandLine.setQuantity(1); // Set default quantity to 1
                commandLine.setPrixOrder(product.getPricePr()); // Set total price as price of one unit
                commandLine.setCart(cart);
                commandelineRepository.save(commandLine);

            } else {
                throw new RuntimeException("Insufficient quantity for product ID: " + idPr);
            }
        }

        // Update the total price of the cart
        totalPrice = calculateTotalPrice(cart);

        // Save changes to the database
        cart.setPriceCart(totalPrice);
        cartRepository.save(cart);
        userRepository.save(user);

    }


    // Calculate total price of the cart
    public static float calculateTotalPrice(Cart cart) {
        float totalPrice = 0.0f;
        for (Commandeline commandLine : cart.getCommandelineList()) {
            totalPrice += commandLine.getPrixOrder();
        }
        return totalPrice;
    }

    public void deleteProductFromCommandelines(Long productId, Long cartId) throws RuntimeException {
        // Retrieve the cart based on its ID
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Check if the cart has any command lines
        if (cart.getCommandelineList().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Retrieve the command line associated with the given product ID
        Commandeline commandLineToRemove = null;
        for (Commandeline commandLine : cart.getCommandelineList()) {
            if (commandLine.getProduct().getIdPr().equals(productId)) {
                commandLineToRemove = commandLine;
                break;
            }
        }

        if (commandLineToRemove == null) {
            throw new RuntimeException("Product not found in the cart");
        }

        // Remove the command line from the cart's list of command lines
        cart.getCommandelineList().remove(commandLineToRemove);

        // Calculate the new total price of the cart
        float totalPrice = calculateTotalPrice(cart);

        // Update the total price of the cart
        cart.setPriceCart(totalPrice);

        // Save changes to the database
        cartRepository.save(cart);
        commandelineRepository.delete(commandLineToRemove); // Delete the command line from the database
    }

}









