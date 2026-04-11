package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.Cart;

public interface CartService {
    Cart getCart(Long userId);
    void addToCart(Long userId, Long productId, Integer quantity, String color);
    void updateQuantity(Long itemId, Integer quantity);
    void removeItem(Long itemId);
    void clearCart(Long userId);
}