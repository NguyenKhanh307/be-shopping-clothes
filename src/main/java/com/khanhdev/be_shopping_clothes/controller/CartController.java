package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.dto.AddToCartRequest;
import com.khanhdev.be_shopping_clothes.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request);
        return ResponseEntity.ok("Added");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(
            @RequestParam Integer cartItemId,
            @RequestParam Integer quantity) {

        cartService.updateQuantity(cartItemId, quantity);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<?> deleteCartItem(@RequestParam Integer cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Deleted");
    }
}