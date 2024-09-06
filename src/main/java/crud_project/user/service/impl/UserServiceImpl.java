package crud_project.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import crud_project.user.dto.UserRequest;
import crud_project.user.model.User;
import crud_project.user.repository.UserRepository;
import crud_project.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> registerUser(UserRequest data) {
        try {
            if(userRepository.findByEmail(data.email()) != null){
                return ResponseEntity.status(409).build();
            }

            String hash = passwordEncoder.encode(data.password());
            User user = new User(data);
            user.setPassword(hash);
            userRepository.save(user);
            return ResponseEntity.ok("Usuário salvo no banco de dados");
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        try {

            List<User> users = userRepository.findAll();

            if(users.isEmpty()){
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<User> getUserById(Long id) {
        try {

            Optional<User> userOptional = userRepository.findById(id);

            if(userOptional.isPresent()){
                User user = userOptional.get();

                return ResponseEntity.ok(user);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
}
