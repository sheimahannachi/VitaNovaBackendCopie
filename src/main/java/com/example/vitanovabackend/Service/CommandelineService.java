package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.DAO.Entities.Product;
import com.example.vitanovabackend.DAO.Repositories.CommandelineRepository;
import com.example.vitanovabackend.DAO.Repositories.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CommandelineService implements CommandelineIService {

    private CommandelineRepository commandelineRepository;
    ProductRepository productRepository;
    public List<Commandeline> getCommandLine() {
        return commandelineRepository.findAll();
    }
    @Override
    public Commandeline getCommandelineById(Long commandelineId) {
        return commandelineRepository.findById(commandelineId).get();
    }

    public void updateCommandelineQuantity(Long commandelineId, int newQuantity) {
        // Retrieve the command line from the database
        Commandeline commandeline = commandelineRepository.findById(commandelineId)
                .orElseThrow(() -> new RuntimeException("Command line not found for ID: " + commandelineId));

        // Update the quantity of the command line
        commandeline.setQuantity(newQuantity);

        // Save the updated command line
        commandelineRepository.save(commandeline);
    }

    public void processCheckout(List<Commandeline> commandelines) {
        for (Commandeline item : commandelines) {
            // Retrieve product from the database
            Product product = productRepository.findById(item.getProduct().getIdPr()).orElseThrow(
                    () -> new NoSuchElementException("Product not found with ID: " + item.getProduct().getIdPr())
            );

            // Check if requested quantity exceeds available quantity
            if (item.getQuantity() > product.getQuantityPr()) {
                throw new IllegalArgumentException("Insufficient quantity for product: " + product.getNamePr());
            }

            // Proceed with payment logic
        }
    }

}
