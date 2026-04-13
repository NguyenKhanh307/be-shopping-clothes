package com.khanhdev.be_shopping_clothes.service.impl;

import com.khanhdev.be_shopping_clothes.entity.*;
import com.khanhdev.be_shopping_clothes.exception.ResourceNotFoundException;
import com.khanhdev.be_shopping_clothes.repository.*;
import com.khanhdev.be_shopping_clothes.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Order createOrderFromCart(Long customerId, String shippingAddress, String note) {
        Cart cart = cartRepository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer: " + customerId));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Giỏ hàng đang trống.");
        }

        // Tính tổng tiền
        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Nếu địa chỉ trống, lấy từ hồ sơ user
        String finalAddress = shippingAddress;
        if (finalAddress == null || finalAddress.trim().isEmpty()) {
            User user = userRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            finalAddress = user.getAddress();
        }

        // Validate địa chỉ cuối cùng - Nếu vẫn trống thì gán địa chỉ mặc định để tránh lỗi 500
        if (finalAddress == null || finalAddress.trim().isEmpty()) {
            finalAddress = "Địa chỉ mặc định (Vui lòng cập nhật hồ sơ)";
        }

        // Tạo Order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setTotalAmount(totalAmount);
        order.setStatus(Order.OrderStatus.pending);
        order.setShippingAddress(finalAddress);
        order.setNote(note);
        order.setOrderNumber(generateOrderNumber());

        // Tạo danh sách OrderItem từ CartItem
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setColor(cartItem.getColor());
            orderItem.setUnitPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();

        // Gán thông tin hiển thị transient
        populateOrderItemsInfo(savedOrder);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
        orders.forEach(this::populateOrderItemsInfo);
        return orders;
    }

    private void populateOrderItemsInfo(Order order) {
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                productRepository.findById(item.getProductId()).ifPresent(product -> {
                    item.setProductName(product.getName());
                    item.setImageUrl(product.getImageUrl());
                });
            }
        }
    }

    private String generateOrderNumber() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomPart = new Random().nextInt(900) + 100; // 100-999
        return "ORD-" + datePart + "-" + randomPart;
    }
}
