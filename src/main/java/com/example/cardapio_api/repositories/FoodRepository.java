package com.example.cardapio_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cardapio_api.infra.exceptions.food.FoodNotFoundException;
import com.example.cardapio_api.domain.food.Food;

public interface FoodRepository extends JpaRepository<Food,Long>{
    void deleteById(long id) throws FoodNotFoundException;
}
