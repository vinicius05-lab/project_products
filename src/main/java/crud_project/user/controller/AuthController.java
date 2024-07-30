package crud_project.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud_project.user.dto.AuthRequest;
import crud_project.user.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping
    public String login(@RequestBody @Valid AuthRequest data){
        var userAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        authenticationManager.authenticate(userAuthenticationToken);

        return authService.getToken(data);
    }
}
