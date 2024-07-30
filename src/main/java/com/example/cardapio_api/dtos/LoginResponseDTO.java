package com.example.cardapio_api.dtos;

import com.example.cardapio_api.domain.user.User;

public record LoginResponseDTO(String token, User user) {
    
}
