package com.example.cardapio_api.infra.exceptions.user;

public class UnauthorizedException extends RuntimeException{

    
    public UnauthorizedException(String message){
        super(message);
    }

    public UnauthorizedException() {
        super("Email ou senha inválidos!");
    }
}