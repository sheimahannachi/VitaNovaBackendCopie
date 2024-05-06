
package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Cart;
import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.CartRepository;
import com.example.vitanovabackend.DAO.Repositories.CommandelineRepository;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CartService implements CartIService{

    CartRepository  cartRepository;
    CommandelineRepository commandelineRepository;
    ProductRepository productRepository;
    UserRepository userRepository;



    public List<Commandeline> getAllCommandelinesInCart(Long userId) {
        User user=userRepository.findById(userId).get();
        Cart cart =user.getCart();
        System.out.println(cart.getPriceCart());
        return cart.getCommandelineList();
    }

    public int getNumberOfCommandelinesInCart(Long userId) {
        User user=userRepository.findById(userId).get();
        Cart cart =user.getCart();
        if (cart != null) {
            return cart.getCommandelineList().size();
        }
        return 0;
    }


    /*
    public void updateProductQuantityInCart(Long cartId, Long productId, int newQuantity) {
        // Rechercher le panier dans la base de données en fonction de l'ID du panier
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        // Rechercher le produit dans la base de données en fonction de l'ID du produit
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // Rechercher la ligne de commande correspondant au produit dans le panier
        Commandeline commandeline = cart.getCommandelineList().stream()
                .filter(cl -> cl.getProduct().getIdPr().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée pour le produit ID: " + productId));

        // Vérifier si la nouvelle quantité est inférieure ou égale à la quantité disponible du produit
        if (newQuantity <= product.getQuantityPr()) {
            // Calculer la différence entre la nouvelle quantité et l'ancienne quantité
            float quantityDifference = newQuantity - commandeline.getQuantity();

            // Mettre à jour la quantité de la ligne de commande
            commandeline.setQuantity(newQuantity);

            // Mettre à jour le prix de la commande pour refléter la nouvelle quantité
            float unitPrice = product.getPricePr();
            float newTotalPrice = unitPrice * newQuantity;
            commandeline.setPrixOrder(newTotalPrice);

            // Mettre à jour le prix total du panier
            float totalPrice = ProductService.calculateTotalPrice(cart);
            cart.setPriceCart(totalPrice);

            // Enregistrer les modifications dans la base de données
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("La quantité du produit dépasse la quantité disponible pour le produit ID: " + productId);
        }
    }
*/


        public void createUserCart(long userId) {
            // Retrieve the user from the database
            User user = userRepository.findById(userId).get();

            // Create a new cart
            Cart cart = new Cart();
            user.setCart(cart);

            // Save the cart to associate it with the user
            cartRepository.save(cart);
            userRepository.save(user);
        }


    public List<Commandeline> getProductsInCart(Long userId) {
        Cart cart = cartRepository.findByUserIdUser(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user ID: " + userId);
        }
        return cart.getCommandelineList();
    }



}
