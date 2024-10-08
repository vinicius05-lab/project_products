package crud_project.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud_project.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findByName(String name);
}