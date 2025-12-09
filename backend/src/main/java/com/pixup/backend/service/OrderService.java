package com.pixup.backend.service;

import com.pixup.backend.entity.Game;
import com.pixup.backend.entity.Order;
import com.pixup.backend.entity.OrderItem;
import com.pixup.backend.entity.User;
import com.pixup.backend.repository.GameRepository;
import com.pixup.backend.repository.OrderRepository;
import com.pixup.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public Order createOrder(String username, List<Long> gameIds, String shippingAddress) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("COMPLETED"); // Simplified status
        order.setShippingAddress(shippingAddress);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Long gameId : gameIds) {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new RuntimeException("Game not found: " + gameId));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setGame(game);
            item.setPrice(game.getPrice());

            orderItems.add(item);
            total = total.add(game.getPrice());
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
}
