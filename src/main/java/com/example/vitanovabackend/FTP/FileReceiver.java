package com.example.vitanovabackend.FTP;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class FileReceiver {
/*
    // Autres injections de dépendances

    @ServiceActivator(inputChannel = "uploadedFilesChannel")
    public void processUploadedFile(Message<File> fileMessage) {
        File file = fileMessage.getPayload();

        // Implémente la logique de traitement du fichier reçu
        try {
            // Exemple: Copier le fichier vers un répertoire de stockage local
            File destinationDirectory = new File("/chemin/vers/le/repertoire/destination");
            Files.copy(file.toPath(), new File(destinationDirectory, file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Autres opérations de traitement, comme enregistrement dans la base de données, etc.

            System.out.println("Fichier reçu et traité avec succès: " + file.getName());
        } catch (IOException e) {
            // Gestion des erreurs en cas de problème lors du traitement du fichier
            System.err.println("Erreur lors du traitement du fichier: " + e.getMessage());
        }
    }*/
}

