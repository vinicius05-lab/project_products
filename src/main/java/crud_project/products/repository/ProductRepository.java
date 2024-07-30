package crud_project.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud_project.products.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findByName(String name);
}