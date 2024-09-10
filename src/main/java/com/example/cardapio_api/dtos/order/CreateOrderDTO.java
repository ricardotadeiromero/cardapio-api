package com.example.cardapio_api.dtos.order;

import java.util.List;

public record CreateOrderDTO(List<OrderItemDTO> items, String client) {
    
}
