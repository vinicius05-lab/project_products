package crud_project.user.service;

import java.time.Instant;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import crud_project.user.dto.AuthRequest;
import crud_project.user.model.User;

public interface AuthService extends UserDetailsService{

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    String getToken(AuthRequest data);

    String generateToken(User user);

    String validateToken(String token);

    Instant generateDate();

}