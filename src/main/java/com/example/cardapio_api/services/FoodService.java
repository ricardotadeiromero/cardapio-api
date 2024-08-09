package com.example.cardapio_api.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.domain.food.FoodType;
import com.example.cardapio_api.dtos.food.CreateFoodDTO;
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

    public Map<String,List<Food>> findAllGrouped() {
        Map<String, List<Food>> groupedFood = new HashMap<>();
        List<Food> list = repository.findAll();
        for (Food food : list) {
            String type = food.getType().getName();
            if (!groupedFood.containsKey(type)) {
                groupedFood.put(type, new ArrayList<>());
            }
            groupedFood.get(type).add(food);
        }
        return groupedFood;
    }

    public Food create(CreateFoodDTO data) {
        Food food = new Food(data);
        return repository.save(food);
    }

    public Food findById(long id) {
        return repository.findById(id).orElseThrow(() -> new FoodNotFoundException());
    }

    public Food update(long id, UpdateFoodDTO data) {
        Food food = repository.findById(id).orElseThrow(() -> new FoodNotFoundException());
        food.setTitle(data.title());
        food.setImage(data.image());
        food.setDescription(data.description());
        food.setIngredients(data.ingredients());
        food.setType(data.type());
        food.setPrice(data.price());
        repository.save(food);
        return food;
    }

    public void delete(long id) {
        this.repository.deleteById(id);
    }
}
