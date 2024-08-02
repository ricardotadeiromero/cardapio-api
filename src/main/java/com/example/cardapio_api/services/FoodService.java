package com.example.cardapio_api.services;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;
import com.example.cardapio_api.infra.exceptions.food.FoodNotFoundException;
import com.example.cardapio_api.repositories.FoodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;

    public List<Food> findAll() {
        List<Food> list = repository.findAll();
        return list;
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
        food.setPrice(data.price());
        repository.save(food);
        return food;
    }

    public void delete(long id) {
        this.repository.deleteById(id);
    }
}
