package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrderFromCart(Long customerId, String shippingAddress, String note);
    List<Order> getOrdersByCustomer(Long customerId);
}
