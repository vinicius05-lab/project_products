package crud_project.category.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import crud_project.category.Service.CategoryService;
import crud_project.category.dto.CategoryRequest;
import crud_project.category.model.Category;
import crud_project.category.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
    
    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<String> registerCategory(CategoryRequest data){
        try {
            if(categoryRepository.findByName(data.name()) != null){
                return ResponseEntity.badRequest().build();
            }

            categoryRepository.save(new Category(data));
            return ResponseEntity.ok("Categoria salva no banco de dados");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<List<Category>> getAlCategories(){
        try {
            List<Category> categories = categoryRepository.findAll();

            if(categories.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(categories);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<Category> getCategoryById(Long id){
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            
            if(categoryOptional.isPresent()){
                Category category = categoryOptional.get();

                return ResponseEntity.ok(category);
            }

            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<Category> updateCategory(Long id, CategoryRequest data){
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            
            if(categoryOptional.isPresent()){
                Category category = categoryOptional.get();
                category.setName(data.name());
                categoryRepository.save(category);

                return ResponseEntity.ok(category);
            }

            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<String> deleteCategory(Long id){
        try {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            
            if(categoryOptional.isPresent()){
                categoryRepository.deleteById(id);

                return ResponseEntity.ok("Categoria Deletada");
            }

            return ResponseEntity.notFound().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}