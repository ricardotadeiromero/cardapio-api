package com.example.cardapio_api.infra.exceptions.type;

public class TypeNotFoundException extends RuntimeException {
    
    public TypeNotFoundException(String message) {
        super(message);
    }

    public TypeNotFoundException() {
        super("Tipo não encontrado");
    }
}
