package com.example.cardapio_api.services;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.cardapio_api.domain.role.Role;
import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.LoginRequestDTO;
import com.example.cardapio_api.dtos.LoginResponseDTO;
import com.example.cardapio_api.dtos.RecoverResponseDTO;
import com.example.cardapio_api.dtos.RegisterRequestDTO;
import com.example.cardapio_api.dtos.UpdateUserDTO;
import com.example.cardapio_api.infra.exceptions.user.UnauthorizedException;
import com.example.cardapio_api.infra.exceptions.user.UserNotFoundException;
import com.example.cardapio_api.infra.security.TokenService;
import com.example.cardapio_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEnconder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public User register(RegisterRequestDTO dto) {
        User user = new User(dto);
        user.setRole(new Role(3, "COMMON"));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (dto.image() != null && !dto.image().isEmpty()) {
            user = saveImage(user, dto.image());
        }
        userRepository.save(user);
        return user;
    }

    public User findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    public User update(UpdateUserDTO dto) {
        User user = userRepository.findById(dto.id()).orElseThrow(() -> new UserNotFoundException());
        
        if (passwordEnconder.matches(dto.password(), user.getPassword())) {
            user.setUsername(dto.username());
            
            // Verifica se o novo password não é vazio e define a nova senha
            if (dto.newPassword() != null && !dto.newPassword().isEmpty()) {
                user.setPassword(new BCryptPasswordEncoder().encode(dto.newPassword()));
            }
            
            // Verifica se a imagem não é nula e não está vazia
            if (dto.image() != null && !dto.image().isEmpty()) {
                user = saveImage(user, dto.image());
            }
            
            userRepository.save(user);
            return user;
        }
        
        throw new UnauthorizedException();
    }
    

    public User saveImage(User user, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes(); // Get bytes directly from the file
            user.setImage(bytes); // Assuming `food.setImage()` can accept `byte[]`
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save image", e);
        }
    }

    public byte[] findImageById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return user.getImage();
    }

    public LoginResponseDTO login(LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) authentication.getPrincipal());
        User user = recover(token);
        return new LoginResponseDTO(token, RecoverResponseDTO.fromEntity(user));
    }

    public User recover(String token) {
        String email = tokenService.validateToken(token);
        return userRepository.findUserByEmail(email)
                .map(user -> new User(user.getId(), user.getEmail(), user.getUsername(), user.getRole(),
                        user.getImage()))
                .orElseThrow(() -> new UnauthorizedException());
    }
}
