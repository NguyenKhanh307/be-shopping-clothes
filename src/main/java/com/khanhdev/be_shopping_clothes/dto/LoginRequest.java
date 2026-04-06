package com.khanhdev.be_shopping_clothes.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;    // plain-text, backend so sánh với hash trong DB
}
