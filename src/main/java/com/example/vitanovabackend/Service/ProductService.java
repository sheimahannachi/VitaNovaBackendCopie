package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

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
            // Retrieve the original filename of the image
            String originalFilename = file.getOriginalFilename();

            // Generate a unique filename using UUID to avoid filename conflicts
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename;

            // Upload the image to the server with the generated filename
            uploadImage(file, fileName);

            // Set the generated filename to the 'picturePr' attribute of the product
            product.setPicturePr(fileName);

            // Set the 'archivePr' attribute of the product to 'false' as the new product should not be archived by default
            product.setArchivePr(false);

            // Save the product in the database
            Product savedProduct = productRepository.save(product);

            // Generate QR code for the product
            generateQRCodeForProduct(savedProduct.getIdPr());

            // Get the QR code URL for the generated QR code
            String qrCodeUrl = "http://localhost:80/aziz/QRCode_" + savedProduct.getIdPr() + ".png";

            // Set the QR code URL to the 'qrCodeUrl' attribute of the saved product
            savedProduct.setQrCodeUrl(qrCodeUrl);
            // Save the product with the QR code URL in the database
            savedProduct = productRepository.save(savedProduct);

            // Return the saved product with the QR code URL
            return savedProduct;
        } catch (IOException e) {
            // Handle IO errors when uploading the image
            e.printStackTrace();
            return null;
        }
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

    @Override
    public boolean addLike(Long idUser, Long idPr) {

            // Recherche de l'utilisateur et du produit correspondants
            User user = userRepository.findById(idUser)
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + idUser));
            Product product = productRepository.findById(idPr)
                    .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + idPr));

            // Vérifier si l'utilisateur a déjà aimé le produit
            if(likeProductRepository.existsByUserAndProduct(user, product)) {
return false;
            }

            // Création du like pour le produit
            LikeProduct likeProduct = new LikeProduct();
            likeProduct.setUser(user);
            likeProduct.setProduct(product);
            likeProduct.setUserId(user.getIdUser());
            likeProduct.setProductId(product.getIdPr());

            // Enregistrer le like dans la base de données
            likeProductRepository.save(likeProduct);

            // Incrémentation du nombre de likes du produit
            product.setLikeCount(product.getLikeCount() + 1);
            productRepository.save(product);

        return true;
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
    public float calculateTotalPrice(Cart cart) {
        // Initialize total price to 0
        float totalPrice = 0.0f;

        // Iterate through each command line in the cart
        for (Commandeline commandeline : cart.getCommandelineList()) {
            // Get the quantity and unit price of the command line
            int quantity = (int) commandeline.getQuantity();
            float unitPrice = commandeline.getProduct().getPricePr();

            // Calculate the price of the command line and add it to the total price
            totalPrice += quantity * unitPrice;
        }

        // Set the total price in the cart
        cart.setPriceCart(totalPrice);

        // Return the total price
        return totalPrice;
    }

    public void deleteProductFromCart(Long userId, Long productId) {
        // Retrieve the user from the database based on userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get the cart associated with the user
        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found for user with ID: " + userId);
        }

        // Find the command line associated with the specified product ID
        Commandeline commandelineToRemove = null;
        for (Commandeline commandeline : cart.getCommandelineList()) {
            if (commandeline.getProduct().getIdPr().equals(productId)) {
                commandelineToRemove = commandeline;
                break;
            }
        }

        if (commandelineToRemove == null) {
            throw new RuntimeException("Commandeline not found for product ID: " + productId);
        }

        // Remove the commandeline from the cart
        cart.getCommandelineList().remove(commandelineToRemove);

        // Update the total price of the cart
        float totalPrice = calculateTotalPrice(cart);
        cart.setPriceCart(totalPrice);

        // Save changes to the database
        commandelineRepository.delete(commandelineToRemove);
    }

    public void generateQRCodeForProduct(Long productId) {
        // Retrieve the product from the database or wherever it's stored
        Product product = getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        // Generate QR code text with the product URL
        String productUrl = "http://localhost:4200/vitaNova/ProductDetails/" + productId; // Modify the URL format as needed
        String qrCodeText = productUrl;

        // Set QR code image width and height
        int width = 250;
        int height = 250;

        // Set file path for saving the QR code image (change as needed)
        String uploadDirectory = "C:/xampp/htdocs/aziz/";
        String fileName = "QRCode_" + productId + ".png";
        String filePath = uploadDirectory + fileName;

        // Generate QR code and save it to file
        try {
            generateQRCode(qrCodeText, width, height, filePath);
            System.out.println("QR Code generated successfully for product ID: " + productId);
        } catch (WriterException | IOException e) {
            System.out.println("Error generating QR Code for product ID " + productId + ": " + e.getMessage());
        }
    }

    private void generateQRCode(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }



    public Map<String, Object> getInvoiceData(Long userId) {
        Map<String, Object> invoiceData = new HashMap<>();

        // Récupérer l'utilisateur
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        // Récupérer le panier de l'utilisateur
        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Panier non trouvé pour l'utilisateur avec l'ID : " + userId);
        }

        // Ajouter la date de la facture
        invoiceData.put("date", LocalDate.now());

        // Ajouter le nom de l'utilisateur
        invoiceData.put("username", user.getUsername());

        // Ajouter les produits avec leur nom, prix et quantité
        List<Map<String, Object>> products = new ArrayList<>();
        float totalPrice = 0;
        for (Commandeline commandeline : cart.getCommandelineList()) {
            Product product = commandeline.getProduct();
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("name", product.getNamePr());
            productMap.put("price", product.getPricePr());
            productMap.put("quantity", commandeline.getQuantity()); // Ajout de la quantité
            products.add(productMap);
            totalPrice += (product.getPricePr() * commandeline.getQuantity());
        }
        invoiceData.put("products", products);

        // Ajouter le prix total
        invoiceData.put("totalPrice", totalPrice);

        return invoiceData;
    }




}