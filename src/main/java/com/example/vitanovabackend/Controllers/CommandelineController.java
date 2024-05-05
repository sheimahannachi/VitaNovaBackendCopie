
package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.Commandeline;
import com.example.vitanovabackend.Service.CommandelineIService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @PutMapping("/{commandelineId}/update")
    public ResponseEntity<?> updateCommandelineQuantity(@PathVariable Long commandelineId,
                                                        @RequestParam("newQuantity") int newQuantity) {
        try {
            commandelineIService.updateCommandelineQuantity(commandelineId, newQuantity);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/checkout")
    public ResponseEntity<String> processCheckout(@RequestBody List<Commandeline> commandelines) {
        try {
            commandelineIService.processCheckout(commandelines);
            // Proceed with payment logic here if needed
            return ResponseEntity.ok("Checkout successful!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during checkout.");
        }
    }
}
