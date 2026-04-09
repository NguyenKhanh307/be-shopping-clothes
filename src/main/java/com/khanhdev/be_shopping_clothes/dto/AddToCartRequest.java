package com.khanhdev.be_shopping_clothes.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String color;
}