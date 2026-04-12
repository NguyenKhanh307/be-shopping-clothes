package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.entity.Order;
import com.khanhdev.be_shopping_clothes.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    /**
     * POST /api/orders/checkout/{customerId}
     * Body: { shippingAddress, note }
     */
    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<?> checkout(
            @PathVariable Long customerId,
            @RequestBody(required = false) Map<String, String> request) {
        try {
            String address = request != null ? request.get("shippingAddress") : null;
            String note = request != null ? request.get("note") : null;
            
            Order order = orderService.createOrderFromCart(customerId, address, note);
            return ResponseEntity.status(201).body(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi server: " + e.getMessage()));
        }
    }

    /**
     * GET /api/orders/user/{customerId}
     */
    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }
}
