package com.example.cardapio_api.dtos.food;

public record CreateFoodDTO(String title, String image, String description, String ingredients, double price) {
    
}
