package com.khanhdev.be_shopping_clothes.dto;

import lombok.Data;


@Data
public class AddToCartRequest {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
