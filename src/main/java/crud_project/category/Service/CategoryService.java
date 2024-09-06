package crud_project.category.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import crud_project.category.dto.CategoryRequest;
import crud_project.category.model.Category;

public interface CategoryService {

    ResponseEntity<String> registerCategory(CategoryRequest data);

    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<Category> getCategoryById(Long id);

    ResponseEntity<Category> updateCategory(Long id, CategoryRequest data);

    ResponseEntity<String> deleteCategory(Long id);
}
