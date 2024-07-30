package com.example.cardapio_api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cardapio_api.domain.role.Role;
import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.LoginRequestDTO;
import com.example.cardapio_api.dtos.LoginResponseDTO;
import com.example.cardapio_api.dtos.RegisterRequestDTO;
import com.example.cardapio_api.infra.exceptions.user.UnauthorizedException;
import com.example.cardapio_api.infra.security.TokenService;
import com.example.cardapio_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public User register(RegisterRequestDTO data) {
        User user = new User(data);
        user.setRole(new Role(3,"COMMON"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) authentication.getPrincipal());
        User user = recover(token);
        return new LoginResponseDTO(token, user);
    }

    public User recover(String token) {
        String email = tokenService.validateToken(token);
        return userRepository.findUserByEmail(email)
                .map(user -> new User(user.getId(), user.getEmail(), user.getUsername(), user.getRole()))
                .orElseThrow(() -> new UnauthorizedException());
    }
}
