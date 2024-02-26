package com.example.vitanovabackend.DAO.Service;

import com.example.vitanovabackend.DAO.Entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductIService {
    public Product addProduct(Product product, MultipartFile file);
    public Product updateProduct(Long IdPr, Product updatedProduct, MultipartFile newImage) ;
    public List<Product > getProducts();
    public void archiverProduit(Long idPr) ;
}
