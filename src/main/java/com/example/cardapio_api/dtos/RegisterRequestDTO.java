package com.example.cardapio_api.dtos;

import com.example.cardapio_api.domain.role.Role;

public record RegisterRequestDTO(String email, String username, String password, Role role) {

}
