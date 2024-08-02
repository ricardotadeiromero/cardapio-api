package com.example.cardapio_api.domain.food;

import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;

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
@Table(name = "foods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String description;
    private String ingredients;
    private double price;

    public Food(CreateFoodDTO dto) {
        this.title = dto.title();
        this.image = dto.image();
        this.price = dto.price();
    }

    public Food(long id, UpdateFoodDTO dto) {
        this.id = id;
        this.title = dto.title();
        this.image = dto.image();
        this.price = dto.price();
    }

}
