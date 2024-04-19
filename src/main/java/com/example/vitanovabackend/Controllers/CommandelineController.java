package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.Service.CommandelineIService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Commandeline")
@MultipartConfig
@CrossOrigin
public class CommandelineController {

    private CommandelineIService commandelineIService;




    @GetMapping
    public ResponseEntity<List<Commandeline>> getCommandLine() {
        List<Commandeline> commandelines = commandelineIService.getCommandLine();
        return new ResponseEntity<>(commandelines, HttpStatus.OK);
    }
    @GetMapping("/{commandelineId}")
    public ResponseEntity<Commandeline> getCommandelineById(@PathVariable Long commandelineId) {
        Commandeline commandeline = commandelineIService.getCommandelineById(commandelineId);
        return ResponseEntity.ok(commandeline);
    }

    @PutMapping("/{commandelineId}")
    public ResponseEntity<?> updateCommandelineQuantity(@PathVariable Long commandelineId,
                                                        @RequestParam("newQuantity") int newQuantity) {
        try {
            commandelineIService.updateCommandelineQuantity(commandelineId, newQuantity);
            return ResponseEntity.ok("La quantité de la ligne de commande a été mise à jour avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
