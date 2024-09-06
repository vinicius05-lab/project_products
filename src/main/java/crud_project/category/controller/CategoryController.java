package crud_project.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud_project.category.Service.impl.CategoryServiceImpl;
import crud_project.category.dto.CategoryRequest;
import crud_project.category.model.Category;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<String> registerCategory(@RequestBody @Valid CategoryRequest data){
        return categoryService.registerCategory(data);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest data){
        return categoryService.updateCategory(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}