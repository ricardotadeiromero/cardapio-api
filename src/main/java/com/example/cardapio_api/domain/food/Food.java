package com.example.cardapio_api.domain.food;

import com.example.cardapio_api.dtos.food.CreateFoodDTO;
import com.example.cardapio_api.dtos.food.UpdateFoodDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
    @Lob
    private byte[] image;
    private String description;
    private String ingredients;
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private FoodType type;

    public Food(CreateFoodDTO dto) {
        this.title = dto.title();
        this.price = dto.price();
        this.description = dto.description();
        this.ingredients = dto.ingredients();
        this.type = dto.type();
    }

    public Food(long id, UpdateFoodDTO dto) {
        this.id = id;
        this.title = dto.title();
        this.price = dto.price();
        this.description = dto.description();
        this.ingredients = dto.ingredients();
        this.type = dto.type();
    }

}
