package com.khanhdev.be_shopping_clothes.service.impl;

import com.khanhdev.be_shopping_clothes.dto.AddToCartRequest;
import com.khanhdev.be_shopping_clothes.entity.Cart;
import com.khanhdev.be_shopping_clothes.entity.CartItem;
import com.khanhdev.be_shopping_clothes.repository.CartItemRepository;
import com.khanhdev.be_shopping_clothes.repository.CartRepository;
import com.khanhdev.be_shopping_clothes.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getOrCreateCart(Integer userId) {
        Optional<Cart> optional = cartRepository.findByUserId(userId);

        if (optional.isPresent()) {
            return optional.get();
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        return cartRepository.save(cart);
    }
    @Override
    public List<CartItem> getCartItems(Integer userId) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.findByCartId(cart.getCartId());
    }

    @Override
    public void addToCart(AddToCartRequest request) {
        Cart cart = getOrCreateCart(request.getUserId());

        Optional<CartItem> existing =
                cartItemRepository.findByCartIdAndProductId(
                        cart.getCartId(),
                        request.getProductId()
                );

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setCartId(cart.getCartId());
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }
    }

    @Override
    public void updateQuantity(Integer cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow();

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Override
    public void deleteCartItem(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}