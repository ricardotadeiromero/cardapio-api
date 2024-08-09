package com.example.cardapio_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.cardapio_api.domain.food.FoodType;
import com.example.cardapio_api.dtos.food.type.CreateTypeDTO;
import com.example.cardapio_api.dtos.food.type.UpdateTypeDTO;
import com.example.cardapio_api.infra.exceptions.type.TypeNotFoundException;
import com.example.cardapio_api.repositories.FoodTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TypeService {
    
    private final FoodTypeRepository repository;

    public List<FoodType> findAll() {
        return repository.findAll();
    }

    public FoodType save(CreateTypeDTO dto) {
        FoodType data = new FoodType(dto);
        return repository.save(data);
    }

    public FoodType update(long id, UpdateTypeDTO dto) {
        FoodType data = repository.findById(id).orElseThrow(() -> new TypeNotFoundException());
        data.setName(dto.name());
        return repository.save(data);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
