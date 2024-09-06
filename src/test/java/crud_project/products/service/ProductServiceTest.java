package crud_project.products.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import crud_project.category.dto.CategoryRequest;
import crud_project.category.model.Category;
import crud_project.category.repository.CategoryRepository;
import crud_project.product.dto.ProductRequest;
import crud_project.product.model.Product;
import crud_project.product.repository.ProductRepository;
import crud_project.product.service.Impl.ProductServiceImpl;

@SpringBootTest
public class ProductServiceTest {
    
    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductServiceImpl productService;

    private final ProductRequest productRequest = new ProductRequest("test", "test", 0, 1, 1L);
    private final CategoryRequest categoryRequest = new CategoryRequest("categoryTest");
    private final Category category = new Category(categoryRequest);
    private final Product product = new Product(productRequest, category);
    private final Optional<Product> productOptional = Optional.of(product);
    private final Optional<Category> categoryOptional = Optional.of(category);
    private final ProductRequest productUpdated = new ProductRequest("productUpdated", "test", 20, 1, 1L);
    private final Long id = 1L;

    @Test
    @DisplayName("should register a product successfully")
    void shouldRegisterProduct(){
        when(productRepository.findByName(productRequest.name())).thenReturn(null);
        
        Optional<Category> categoryOptional = Optional.of(new Category(categoryRequest));
        when(categoryRepository.findById(productRequest.categoryId())).thenReturn(categoryOptional);

        ResponseEntity<String> response = productService.registerProduct(productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    @DisplayName("should not register an existing product")
    void shouldNotRegisterExistingProduct(){
        when(productRepository.findByName(productRequest.name())).thenReturn(product);

        ResponseEntity<String> response = productService.registerProduct(productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(productRepository, never()).save(any(Product.class));

    }

    @Test
    @DisplayName("should not register product when category does not exist")
    void shouldNotRegisterProductWhenCategoryDoesNotExist(){
        when(productRepository.findByName(productRequest.name())).thenReturn(null);
        when(categoryRepository.findById(productRequest.categoryId())).thenReturn(Optional.empty());

        ResponseEntity<String> response = productService.registerProduct(productRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("should get all products")
    void shouldGetAllProducts(){
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productService.getAllProducts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(products);
    }

    @Test
    @DisplayName("should not get all products")
    void shouldNotGetAllProducts(){
        when(productRepository.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<List<Product>> response = productService.getAllProducts();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("should get product by id")
    void shouldGetProductById(){
        when(productRepository.findById(id)).thenReturn(productOptional);

        ResponseEntity<Product> response = productService.getProductById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
    }

    @Test
    @DisplayName("should not get product by id")
    void shouldNotGetProductById(){
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        
        ResponseEntity<Product> response = productService.getProductById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("should update product")
    void shouldUpdateProduct(){
        when(productRepository.findById(id)).thenReturn(productOptional);
        when(categoryRepository.findById(productUpdated.categoryId())).thenReturn(categoryOptional);

        ResponseEntity<Product> response = productService.updateProduct(id, productUpdated);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(productRepository, times(1)).save(any(Product.class));
        
    }

    @Test
    @DisplayName("should not update product when does not exist")
    void shouldNotUpdateProductWhenDoesNotExist(){
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productService.updateProduct(id, productUpdated);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("should not update product when category does not exist")
    void shouldNotUpdateProductWhenCategoryDoesNotExist(){
        when(productRepository.findById(id)).thenReturn(productOptional);
        when(categoryRepository.findById(productRequest.categoryId())).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productService.updateProduct(id, productUpdated);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("should delete product")
    void shouldDeleteProduct(){
        when(productRepository.findById(id)).thenReturn(productOptional);

        ResponseEntity<String> response = productService.deleteProduct(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("should not delete product")
    void shouldNotDeleteProduct(){
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = productService.deleteProduct(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(productRepository, never()).deleteById(id);
    }

}