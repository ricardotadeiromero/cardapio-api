package com.example.cardapio_api.services;

import org.springframework.stereotype.Service;
import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.domain.order.Order;
import com.example.cardapio_api.domain.order.OrderItem;
import com.example.cardapio_api.domain.order.Status;
import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.order.CreateOrderDTO;
import com.example.cardapio_api.dtos.order.OrderItemDTO;
import com.example.cardapio_api.infra.exceptions.order.OrderNotFoundException;
import com.example.cardapio_api.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final FoodService foodService;
    private final AuthenticationService authService;

    public Order create(CreateOrderDTO dto) {
        double total = 0.0;
        List<OrderItem> items = new ArrayList<>();
        
        // Loop para adicionar OrderItem e calcular o total
        for (OrderItemDTO itemDTO : dto.items()) {
            Food food = foodService.findById(itemDTO.foodId());
    

            OrderItem orderItem = new OrderItem(food, itemDTO.amount());
            items.add(orderItem);

            // Adiciona o valor ao total
            total += food.getPrice() * itemDTO.amount();
        }

        User client = authService.findById(dto.client());

        Order order = new Order(dto, items, client);
        order.setAmount(total);
        order.setStatus(Status.ACTIVE);

        return repository.save(order);
    }

    public List<Order> findAllActive() {
        return repository.findActiveOrders();
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order changeStatus(String id, Status status) {
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException());
        order.setStatus(status);
        return repository.save(order);
    }
}
