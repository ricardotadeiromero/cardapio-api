package com.example.cardapio_api.dtos.food;

import com.example.cardapio_api.domain.food.FoodType;

public record UpdateFoodDTO(String title, String image, String description, String ingredients, double price, FoodType type) {
    
}
