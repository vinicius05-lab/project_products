package crud_project.product.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import crud_project.category.model.Category;
import crud_project.category.repository.CategoryRepository;
import crud_project.product.dto.ProductRequest;
import crud_project.product.model.Product;
import crud_project.product.repository.ProductRepository;
import crud_project.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<String> registerProduct(ProductRequest data){
        try {
            if(productRepository.findByName(data.name()) != null){
                return ResponseEntity.status(409).build();
            }

           if(data.categoryId() == null){
                return ResponseEntity.status(400).build();
           }

            Optional<Category> categoryOptional = categoryRepository.findById(data.categoryId());

            if(categoryOptional.isPresent()){
                productRepository.save(new Product(data, categoryOptional.get()));
                return ResponseEntity.ok("Produto salvo no banco de dados");
            }

            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<List<Product>> getAllProducts(){
        try {
            List<Product> products = productRepository.findAll();

            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Product> getProductById(Long id) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);

            if(productOptional.isPresent()){
                Product product = productOptional.get();
                return ResponseEntity.ok(product);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long id, ProductRequest data) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);

            if(productOptional.isPresent()){
                Product product = productOptional.get();
                Optional<Category> categoryOptional = categoryRepository.findById(data.categoryId());
                
                if(categoryOptional.isPresent()){
                    product.setName(data.name());
                    product.setDescription(data.description());
                    product.setPrice(data.price());
                    product.setQuantity(data.quantity());
                    product.setCategory(categoryOptional.get());

                    productRepository.save(product);

                    return ResponseEntity.ok(product);
                }

                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {

            Optional<Product> productOptional = productRepository.findById(id);

            if(productOptional.isPresent()){
                productRepository.deleteById(id);
                return ResponseEntity.ok("Produto Deletado");
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    
} 