package com.example.cardapio_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import com.example.cardapio_api.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("SELECT o FROM Order o WHERE o.status = 'ACTIVE' OR o.status = 'ONGOING'")
    List<Order> findActiveOrders();
}
