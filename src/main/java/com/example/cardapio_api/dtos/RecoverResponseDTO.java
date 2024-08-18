package com.example.cardapio_api.dtos;

import com.example.cardapio_api.domain.role.Role;
import com.example.cardapio_api.domain.user.User;

public record RecoverResponseDTO(String id, String username, String email, Role role, String image) {

    public static RecoverResponseDTO fromEntity(User user) {
        String imageUrl = "";
        if (user.getImage() != null) {
            imageUrl = "http://localhost:8080/auth/" + user.getId() + "/image";
        }
        return new RecoverResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), imageUrl);

    }
}
