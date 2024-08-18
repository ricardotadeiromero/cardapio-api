package com.example.cardapio_api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.domain.food.FoodType;
import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.FoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;
import com.example.cardapio_api.infra.exceptions.food.FoodNotFoundException;
import com.example.cardapio_api.repositories.FoodRepository;
import com.example.cardapio_api.repositories.FoodTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;
    private final FoodTypeRepository typeRepository;

    public List<Food> findAll() {
        List<Food> list = repository.findAll();
        return list;
    }

    public List<FoodType> findAllTypes() {
        return typeRepository.findAll();
    }

    public Map<String, List<FoodDTO>> findAllGrouped() {
        Map<String, List<FoodDTO>> groupedFood = new HashMap<>();
        List<FoodDTO> list = repository.findAll().stream()
        .map(FoodDTO::fromEntity)
        .collect(Collectors.toList());
        for (FoodDTO food : list) {
            String type = food.type().getName();
            if (!groupedFood.containsKey(type)) {
                groupedFood.put(type, new ArrayList<>());
            }
            groupedFood.get(type).add(food);
        }
        return groupedFood;
    }

    public Food create(CreateFoodDTO data) {
        Food food = new Food(data);
        food = saveImage(food,data.image());
        return repository.save(food);
    }

    private Food saveImage(Food food, MultipartFile file) {
        try {
            byte[] bytes = file.getBytes(); // Get bytes directly from the file
            food.setImage(bytes); // Assuming `food.setImage()` can accept `byte[]`
            return food;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save image", e);
        }
    }

    public Food findById(long id) {
        return repository.findById(id).orElseThrow(() -> new FoodNotFoundException());
    }

    public byte[] findImageById(long id) {
        Food food = repository.findById(id).orElseThrow(() -> new FoodNotFoundException());
        return food.getImage();
    }

    public Food update(long id, UpdateFoodDTO data) {
        Food food = repository.findById(id).orElseThrow(() -> new FoodNotFoundException());
        food.setTitle(data.title());
        food.setDescription(data.description());
        food.setIngredients(data.ingredients());
        food.setType(data.type());
        food.setPrice(data.price());
        if(!data.image().isEmpty()) {
            food = saveImage(food, data.image());
        }
        repository.save(food);
        return food;
    }

    public void delete(long id) {
        this.repository.deleteById(id);
    }
}
