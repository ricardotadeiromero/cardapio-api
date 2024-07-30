package com.example.cardapio_api.infra.exceptions.food;

public class FoodNotFoundException extends RuntimeException{
    
    public FoodNotFoundException(String message) {
        super(message);
    }

    public FoodNotFoundException() {
        super("Item não encontrado!");
    }
}
