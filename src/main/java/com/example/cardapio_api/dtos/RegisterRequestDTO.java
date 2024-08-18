package com.example.cardapio_api.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.example.cardapio_api.domain.role.Role;

public record RegisterRequestDTO(String email, MultipartFile image, String username, String password, Role role) {

}
