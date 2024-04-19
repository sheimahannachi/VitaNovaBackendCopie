package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.DAO.Repositories.CommandelineRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CommandelineService implements CommandelineIService {

    private CommandelineRepository commandelineRepository;

    public List<Commandeline> getCommandLine() {
        return commandelineRepository.findAll();
    }
    @Override
    public Commandeline getCommandelineById(Long commandelineId) {
        return commandelineRepository.findById(commandelineId).get();
    }

    public void updateCommandelineQuantity(Long commandelineId, int newQuantity) {
        // Rechercher la commande ligne (commandeline) dans la base de données
        Commandeline commandeline = commandelineRepository.findById(commandelineId)
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée pour l'ID : " + commandelineId));

        // Mettre à jour la quantité de la commande ligne
        commandeline.setQuantity(newQuantity);

        // Enregistrer les modifications dans la base de données
        commandelineRepository.save(commandeline);
    }
}
