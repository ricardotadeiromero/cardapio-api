package com.example.cardapio_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cardapio_api.domain.food.FoodType;

public interface FoodTypeRepository extends JpaRepository<FoodType,Long>{
    
}
