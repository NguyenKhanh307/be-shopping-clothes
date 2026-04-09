package com.khanhdev.be_shopping_clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khanhdev.be_shopping_clothes.dto.AddToCartRequest;
import com.khanhdev.be_shopping_clothes.entity.Cart;
import com.khanhdev.be_shopping_clothes.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest req) {
        cartService.addToCart(req.getUserId(), req.getProductId(), req.getQuantity(), req.getColor());
        return ResponseEntity.ok("Item added to cart");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam Long itemId, @RequestParam Integer quantity) {
        cartService.updateQuantity(itemId, quantity);
        return ResponseEntity.ok("Cart item updated");
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> delete(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.ok("Cart item removed");
    }
}