package crud_project.category.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import crud_project.category.Service.impl.CategoryServiceImpl;
import crud_project.category.dto.CategoryRequest;
import crud_project.category.model.Category;
import crud_project.category.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private final Long id = 1L;
    private final String name = "test";
    private final CategoryRequest categoryRequest = new CategoryRequest(name);
    private final CategoryRequest categoryUpdate = new CategoryRequest("categoryUpdate");
    private final Category category = new Category(categoryRequest);
    private final Optional<Category> categoryOptional =  Optional.of(category);

    @Test
    @DisplayName("Should register a category successfully")
    void shouldRegisterCategorySuccessful(){
        when(categoryRepository.findByName(name)).thenReturn(null);
        
        ResponseEntity<String> response = categoryService.registerCategory(categoryRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("should not register an existing category")
    void shouldNotRegisterExistingCategory(){
        when(categoryRepository.findByName(name)).thenReturn(category);
        
        ResponseEntity<String> response = categoryService.registerCategory(categoryRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should get all categories")
    void shouldGetAllCategories(){

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryService.getAllCategories();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(categories);
    }

    @Test
    @DisplayName("Should not get all categories")
    void shouldNotGetAllCategories(){
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<List<Category>> response = categoryService.getAllCategories();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should get a category by id successfully")
    void shouldGetCategoryById(){
        when(categoryRepository.findById(id)).thenReturn(categoryOptional);

        ResponseEntity<Category> response = categoryService.getCategoryById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(category);
    }
    
    @Test
    @DisplayName("Should not get a category by id successfully")
    void shouldNotGetCategoryById(){
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryService.getCategoryById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
    @Test
    @DisplayName("Should update a category successfully")
    void shouldUpdateCategory(){
        when(categoryRepository.findById(id)).thenReturn(categoryOptional);

        ResponseEntity<Category> response = categoryService.updateCategory(id, categoryUpdate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Should not update a category successfully")
    void shouldNotUpdateCategory(){
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Category> response = categoryService.updateCategory(id, categoryUpdate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("Should delete a category successfully")
    void shouldDeleteCategory(){
        when(categoryRepository.findById(id)).thenReturn(categoryOptional);

        ResponseEntity<String> response = categoryService.deleteCategory(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should not delete a category successfully")
    void shouldNotDeleteCategory(){
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = categoryService.deleteCategory(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(categoryRepository, never()).deleteById(id);

    }

}