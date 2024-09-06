package crud_project.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import crud_project.user.dto.UserRequest;
import crud_project.user.model.User;
import crud_project.user.repository.UserRepository;
import crud_project.user.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceTest {
    
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    private final String email = "user@exemple.com";
    private final UserRequest userRequest = new UserRequest("teste", email, "user123");
    private final User user = new User(userRequest);
    private final Optional<User> userOptional = Optional.of(user);
    private final Long id = 1L;

    @Test
    @DisplayName("should register a user sucessifully")
    void shouldRegisterUser(){
        when(userRepository.findByEmail(userRequest.email())).thenReturn(null);
        when(passwordEncoder.encode(userRequest.password())).thenReturn("passwordEncrypted");

        ResponseEntity<String> response = userService.registerUser(userRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    @DisplayName("should not register a user sucessifully")
    void shouldNotRegisterUser(){
        when(userRepository.findByEmail(userRequest.email())).thenReturn(user);
        
        ResponseEntity<String> response = userService.registerUser(userRequest);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("should find all users sucessifully")
    void shouldGetAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userService.getAllUsers();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("should not find all users sucessifully")
    void shouldNotGetAllUsers(){
        when(userRepository.findAll()).thenReturn(List.of());

        ResponseEntity<List<User>> response = userService.getAllUsers();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("should find user by id")
    void shouldGetUserById(){
        when(userRepository.findById(id)).thenReturn(userOptional);

        ResponseEntity<User> response = userService.getUserById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("should not find user by id")
    void shouldNotGetUserById(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userService.getUserById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}