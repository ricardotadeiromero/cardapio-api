package com.example.cardapio_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.cardapio_api.domain.food.Food;
import com.example.cardapio_api.domain.food.FoodType;
import com.example.cardapio_api.domain.order.Order;
import com.example.cardapio_api.domain.order.OrderItem;
import com.example.cardapio_api.domain.order.Status;
import com.example.cardapio_api.domain.role.Role;
import com.example.cardapio_api.domain.user.User;
import com.example.cardapio_api.dtos.order.CreateOrderDTO;
import com.example.cardapio_api.dtos.order.OrderItemDTO;
import com.example.cardapio_api.repositories.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.*;

class OrderServiceTest {

    @InjectMocks
    @Autowired
    OrderService service;

    @Mock
    AuthenticationService authService;

    @Mock
    OrderRepository repository;

    @Mock
    FoodService foodService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser() {
        byte[] userImage = "userimage".getBytes();
        Role role = new Role(1, "COMMOM");
        User client = new User("id", "userteste", "usertest@gmail.com", "password", role, userImage);
        return client;
    }

    @Test
    @DisplayName("Should create a Order successfuly")
    public void testCreateOrder() {
        // Configurando os dados de teste
        byte[] image = "foodimage".getBytes();
        Food food1 = new Food(1L, "Pizza", image, "teste", "teste", 10.0,
                new FoodType(1L, "massa"));
        Food food2 = new Food(2L, "Burger", image, "teste", "teste", 5.0,
                new FoodType(1L, "lanche"));

        OrderItemDTO item1 = new OrderItemDTO(1L, 2); // 2 pizzas
        OrderItemDTO item2 = new OrderItemDTO(2L, 3); // 3 burgers

        List<OrderItemDTO> items = Arrays.asList(item1, item2);

        User client = createUser();

        CreateOrderDTO orderDTO = new CreateOrderDTO(items, client.getId()); // Pedido feito pelo cliente com id 1

        // Configurando os mocks
        when(foodService.findById(1L)).thenReturn(food1);
        when(foodService.findById(2L)).thenReturn(food2);
        when(authService.findById(client.getId())).thenReturn(client);
        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chamando o método a ser testado
        Order order = service.create(orderDTO);

        // Verificações
        assertNotNull(order);
        assertEquals(35.0, order.getAmount()); // 2 * 10.0 + 3 * 5.0 = 35.0
        assertEquals(2, order.getItems().size());
        assertEquals(Status.ACTIVE, order.getStatus());
        assertEquals(client, order.getClient());

        verify(repository, times(1)).save(order);
    }

    @Test
    @DisplayName("Should change status of a order successfully when everything is okay")
    void changeStatusOk() {
        byte[] image = "foodimage".getBytes();
        Food food1 = new Food(1L, "Pizza", image, "teste", "teste", 10.0,
                new FoodType(1L, "massa"));
        Food food2 = new Food(2L, "Burger", image, "teste", "teste", 5.0,
                new FoodType(1L, "lanche"));

        OrderItem item1 = new OrderItem(food1, 2); // 2 pizzas
        OrderItem item2 = new OrderItem(food2, 3); // 3 burgers

        List<OrderItem> items = Arrays.asList(item1, item2);

        User client = createUser();

        Order order = new Order("id", 35.0, items, client, Status.ACTIVE);

        when(repository.findById("id")).thenReturn(Optional.of(order));
        service.changeStatus("id", Status.ONGOING);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(repository, times(1)).save(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();
        verify(repository, times(1)).findById("id");
        assertEquals(Status.ONGOING, savedOrder.getStatus());
    }
}
