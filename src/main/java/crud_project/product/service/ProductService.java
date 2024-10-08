package crud_project.product.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import crud_project.product.dto.ProductRequest;
import crud_project.product.model.Product;

public interface ProductService {
    
    ResponseEntity<String> registerProduct(ProductRequest data);

    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<Product> getProductById(Long id);

    ResponseEntity<Product> updateProduct(Long id, ProductRequest data);

    ResponseEntity<String> deleteProduct(Long id);    
}
