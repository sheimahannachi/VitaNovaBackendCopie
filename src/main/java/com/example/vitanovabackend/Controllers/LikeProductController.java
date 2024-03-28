package com.example.vitanovabackend.Controllers;


import com.example.vitanovabackend.DAO.Repositories.LikeProductRepository;
import com.example.vitanovabackend.Service.LikeProductIService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/Product")
@MultipartConfig
@CrossOrigin(origins="*")
public class LikeProductController {
/*
    private  LikeProductIService likeProductIService;




    @PostMapping("/addLike")
    public ResponseEntity<String> addLike(@RequestParam("idUser") Long idUser, @RequestParam("idPr") Long idPr) {
        // Appelez le service pour ajouter un like
        likeProductIService.addLike(idUser, idPr);
        return ResponseEntity.ok("Like ajouté avec succès !");
    }*/
}
