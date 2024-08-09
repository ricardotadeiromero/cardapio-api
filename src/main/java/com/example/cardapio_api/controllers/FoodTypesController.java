package com.example.cardapio_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapio_api.domain.food.FoodType;
import com.example.cardapio_api.dtos.food.type.CreateTypeDTO;
import com.example.cardapio_api.dtos.food.type.UpdateTypeDTO;
import com.example.cardapio_api.services.TypeService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("types")
@RequiredArgsConstructor
public class FoodTypesController {
    
    private final TypeService service;

    @GetMapping
    public List<FoodType> findAll() {
        return service.findAll();
    }

    @PostMapping
    public FoodType save(@RequestBody CreateTypeDTO dto) {
        FoodType data = service.save(dto);
        return data;
    }

    @PutMapping("{id}")
    public FoodType update(@PathVariable long id, @RequestBody UpdateTypeDTO dto) {
        FoodType data = service.update(id, dto);
        return data;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
}
