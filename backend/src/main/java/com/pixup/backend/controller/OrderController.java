package com.pixup.backend.controller;

import com.pixup.backend.entity.Order;
import com.pixup.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> payload) {
        try {
            String username = (String) payload.get("username");
            String address = (String) payload.get("address");
            List<Integer> gameIdsInt = (List<Integer>) payload.get("gameIds");
            // Convert Integer list to Long
            List<Long> gameIds = gameIdsInt.stream().map(Integer::longValue).toList();

            Order order = orderService.createOrder(username, gameIds, address);
            // Break circular reference or use DTO. For MVP returning Order might cause
            // stack overflow if JSON infinite recursion.
            // Better to return simple ID or DTO.
            return ResponseEntity.ok(Map.of("message", "Order placed successfully", "orderId", order.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(@RequestParam String username) {
        // In a real app, get user from SecurityContext. Here trusting param for MVP.
        return ResponseEntity.ok(orderService.getUserOrders(username));
    }
}
