package com.example.cardapio_api.infra.exceptions.order;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super("Serviço não encontrado!");
    }
    
}
