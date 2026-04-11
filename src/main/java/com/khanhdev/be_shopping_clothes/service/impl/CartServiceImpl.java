package com.khanhdev.be_shopping_clothes.service.impl;

import org.springframework.stereotype.Service;

import com.khanhdev.be_shopping_clothes.entity.Cart;
import com.khanhdev.be_shopping_clothes.entity.CartItem;
import com.khanhdev.be_shopping_clothes.entity.Product;
import com.khanhdev.be_shopping_clothes.exception.ResourceNotFoundException;
import com.khanhdev.be_shopping_clothes.repository.CartItemRepository;
import com.khanhdev.be_shopping_clothes.repository.CartRepository;
import com.khanhdev.be_shopping_clothes.repository.ProductRepository;
import com.khanhdev.be_shopping_clothes.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart getCart(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId must not be null");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });

        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                productRepository.findById(item.getProductId()).ifPresent(product -> {
                    item.setProductName(product.getName());
                    item.setImageUrl(product.getImageUrl());
                });
            }
        }
        return cart;
    }

    @Override
    public void addToCart(Long userId, Long productId, Integer quantity, String color) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Cart cart = getCart(userId);

        CartItem item = cartItemRepository
                .findByCartIdAndProductIdAndColor(cart.getId(), productId, color)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCart(cart);
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setColor(color);
            item.setPrice(product.getSalePrice() != null ? product.getSalePrice().doubleValue() : 0.0); 
        }

        cartItemRepository.save(item);
    }

    @Override
    public void updateQuantity(Long itemId, Integer quantity) {

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (quantity == null) {
            throw new IllegalArgumentException("Quantity must not be null");
        }

        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }

    @Override
    public void removeItem(Long itemId) {

        if (!cartItemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("Cart item not found");
        }

        cartItemRepository.deleteById(itemId);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            cartItemRepository.deleteAll(cart.getItems());
            cart.getItems().clear();
        }
    }
}