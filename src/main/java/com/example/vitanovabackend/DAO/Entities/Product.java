package com.example.vitanovabackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPr;
    String namePr;

    String categoriePr;
    float pricePr ;
    String picturePr;
    String descriptionPr ;
    Long quantityPr;
    String statusPr;
    boolean archivePr;
    int likeCount;
    private String qrCodeUrl;

    @ManyToOne
    private FTPServe ftpServe;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    @JsonIgnore
    List<LikeProduct> likeProducts = new ArrayList<>();



}