package crud_project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crud_project.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
}
