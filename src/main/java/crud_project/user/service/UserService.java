package crud_project.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import crud_project.user.dto.UserRequest;
import crud_project.user.model.User;

public interface UserService{

    ResponseEntity<String> registerUser(UserRequest data);

    ResponseEntity<List<User>> getAllUsers();
    
    ResponseEntity<User> getUserById(Long id);
}