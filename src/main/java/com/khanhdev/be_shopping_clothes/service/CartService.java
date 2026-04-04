package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.dto.AddToCartRequest;
import com.khanhdev.be_shopping_clothes.entity.Cart;
import com.khanhdev.be_shopping_clothes.entity.CartItem;

import java.util.List;

public interface CartService {
    Cart getOrCreateCart(Integer userId);
    List<CartItem> getCartItems(Integer cartId);
    void addToCart(AddToCartRequest request);
    void updateQuantity(Integer cartItemId, Integer quantity);
    void deleteCartItem(Integer cartItemId);
}
