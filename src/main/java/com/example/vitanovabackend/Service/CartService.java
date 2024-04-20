package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Cart;
import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Repositories.CartRepository;
import com.example.vitanovabackend.DAO.Repositories.CommandelineRepository;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CartService implements CartIService{

    CartRepository  cartRepository;
    CommandelineRepository commandelineRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    public void addProductToCart(Long idUser, Long idPr, Long quantity) {
        // Vérifier si l'utilisateur a déjà un panier
        Cart cart = cartRepository.findByUserIdUser(idUser);

        if (cart == null) {
            // Si l'utilisateur n'a pas de panier, créer un nouveau panier
            cart = new Cart();
            cart.setUser(userRepository.findById(idUser).get());
        }

        // Récupérer le produit
        Product product = productRepository.findById(idPr).get();

        // Créer une nouvelle ligne de commande
        Commandeline nouvelleCommandeLigne = new Commandeline();
        nouvelleCommandeLigne.setProduct(product);
        nouvelleCommandeLigne.setQuantity(quantity);
        nouvelleCommandeLigne.setPrixOrder(product.getPricePr() * quantity);
        nouvelleCommandeLigne.setDateOrder(LocalDate.now());

        // Ajouter la nouvelle ligne de commande au panier
        cart.getCommandelineList().add(nouvelleCommandeLigne);

        // Mettre à jour le prix total du panier
        float nouveauPrixTotal = cart.getPriceCart() + (product.getPricePr() * quantity);
        cart.setPriceCart(nouveauPrixTotal);

        // Sauvegarder les modifications du panier
        cartRepository.save(cart);
    }
}
