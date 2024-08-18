package com.example.cardapio_api.dtos.food;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.domain.food.FoodType;

public record FoodDTO(Long id, String title, String description, String ingredients, Double price, FoodType type,
        String image) {

    public static FoodDTO fromEntity(Food food) {
        String imageUrl = "";
        if(food.getImage()!=null) {
            imageUrl = "http://localhost:8080/food/" + food.getId() + "/image";
        }
        return new FoodDTO(food.getId(), food.getTitle(), food.getDescription(), food.getIngredients(), food.getPrice(),
                food.getType(),
                imageUrl);
    }
}
