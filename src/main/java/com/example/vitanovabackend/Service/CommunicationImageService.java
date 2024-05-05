package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.Configuration.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
@Service
public class CommunicationImageService {
    FileUtils fileUtils;

    public String addImage(MultipartFile image){
        if (image != null && !image.isEmpty() && image.getSize() > 0) {
            String uploadDir = "C:/xampp/htdocs/CommunicationsImages/";
            File uploadDirPath = new File(uploadDir);

            if (!uploadDirPath.exists()) {
                boolean created = uploadDirPath.mkdirs();
                if (!created) {
                    // Gérer l'échec de la création du répertoire
                    return null;
                }
            }

            String newPhotoName = fileUtils.nameFile(image);

            try {
                fileUtils.saveNewFile(uploadDir, newPhotoName, image);
                return newPhotoName;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return null;

    }
}
