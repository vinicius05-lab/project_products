package crud_project.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud_project.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByName(String name);
}
