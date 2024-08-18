package com.example.cardapio_api.dtos.food;

import org.springframework.web.multipart.MultipartFile;

import com.example.cardapio_api.domain.food.FoodType;

public record UpdateFoodDTO(String title, MultipartFile image, String description, String ingredients, Double price, FoodType type) {
    
}
