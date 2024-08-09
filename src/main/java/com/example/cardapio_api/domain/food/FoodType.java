package com.example.cardapio_api.domain.food;

import com.example.cardapio_api.dtos.food.type.CreateTypeDTO;
import com.example.cardapio_api.dtos.food.type.UpdateTypeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "foods_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public FoodType(CreateTypeDTO dto) {
        this.name = dto.name();
    }

    public FoodType(long id,UpdateTypeDTO dto) {
        this.id = id;
        this.name = dto.name();
    }
}
