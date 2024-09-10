package com.example.cardapio_api.domain.order;

import java.util.List;

import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.order.CreateOrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double amount;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client")
    @JsonIgnoreProperties({"image", "password"})
    private User client;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(CreateOrderDTO dto, List<OrderItem> items, User client) {
        this.client = client;
        this.items = items;
        this.status = Status.ACTIVE;
    
        for (OrderItem item : items) {
            item.setOrder(this);
        }
    }
}
