package com.example.cardapio_api.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.FoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;
import com.example.cardapio_api.services.FoodService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService service;

    @PostMapping
    public ResponseEntity<FoodDTO> create(@ModelAttribute CreateFoodDTO dto) {
        System.out.println(dto.image());
        Food food = service.create(dto);
        return ResponseEntity.ok(FoodDTO.fromEntity(food));
    }

    @PutMapping("{id}")
    public ResponseEntity<FoodDTO> update(@PathVariable long id, @ModelAttribute UpdateFoodDTO dto) {
        System.out.println(dto.image());
        Food food = service.update(id, dto);
        return ResponseEntity.ok(FoodDTO.fromEntity(food));
    }

    @GetMapping("{id}")
    public ResponseEntity<FoodDTO> getMethodName(@PathVariable long id) {
        Food food = service.findById(id);
        return ResponseEntity.ok(FoodDTO.fromEntity(food));
    }

    @GetMapping("{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable long id) {
        byte[] image = service.findImageById(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteFood(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<FoodDTO>> findAll() {
        List<Food> foods = service.findAll();
        return ResponseEntity.ok(foods.stream()
                .map(FoodDTO::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("types")
    public Map<String, List<FoodDTO>> findAllGrouped() {
        return service.findAllGrouped();
    }
}
