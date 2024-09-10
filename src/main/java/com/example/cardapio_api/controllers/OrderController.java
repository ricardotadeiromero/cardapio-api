package com.example.cardapio_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardapio_api.domain.order.Order;
import com.example.cardapio_api.dtos.order.CreateOrderDTO;
import com.example.cardapio_api.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderDTO dto) {
        Order order = service.create(dto);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Order>> findActiveOrders() {
        List<Order> list = service.findAllActive();
        return ResponseEntity.ok().body(list);
    }
}
