package com.example.cardapio_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.FindFoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodResponseDTO;
import com.example.cardapio_api.services.FoodService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;

    @PostMapping
    public Food create(@RequestBody @Validated CreateFoodDTO dto) {
        return service.create(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Validated UpdateFoodDTO dto) {
        Food food = service.update(id, dto);
        return ResponseEntity.ok(new UpdateFoodResponseDTO(food));
    }

    @GetMapping("{id}")
    public Food getMethodName(@PathVariable long id) {
        Food food = service.findById(id);
        return food;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFood(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Food> findAll() {
        return service.findAll();
    }
}
