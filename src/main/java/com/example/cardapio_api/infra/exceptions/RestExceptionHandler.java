package com.example.cardapio_api.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.cardapio_api.infra.exceptions.food.FoodNotFoundException;
import com.example.cardapio_api.infra.exceptions.type.TypeNotFoundException;
import com.example.cardapio_api.infra.exceptions.user.UnauthorizedException;
import com.example.cardapio_api.infra.exceptions.user.UserNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<String> unauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(FoodNotFoundException.class)
    private ResponseEntity<String> foodNotFoundException(FoodNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TypeNotFoundException.class)
    private ResponseEntity<String> typeNotFoundException(TypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class) 
    private ResponseEntity<String> userNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
