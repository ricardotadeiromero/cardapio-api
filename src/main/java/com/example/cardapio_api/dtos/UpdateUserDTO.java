package com.example.cardapio_api.dtos;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserDTO(String id, String username, String password, String newPassword, MultipartFile image) {
    
}
