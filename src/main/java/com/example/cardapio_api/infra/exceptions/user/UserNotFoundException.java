package com.example.cardapio_api.infra.exceptions.user;

public class UserNotFoundException extends RuntimeException{
    
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
