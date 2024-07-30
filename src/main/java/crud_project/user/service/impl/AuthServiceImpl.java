package crud_project.user.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import crud_project.user.dto.AuthRequest;
import crud_project.user.model.User;
import crud_project.user.repository.UserRepository;
import crud_project.user.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public Instant generateDate() {
        return LocalDateTime
                .now()
                .plusHours(24)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    @Override
    public String getToken(AuthRequest data) {
        return generateToken(userRepository.findByEmail(data.email()));
    }

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            
            return JWT
                    .create()
                    .withIssuer("crud_project")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateDate())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o token");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            
            return JWT
                    .require(algorithm)
                    .withIssuer("crud_project")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token Inválido");
        }
    }
    
}
