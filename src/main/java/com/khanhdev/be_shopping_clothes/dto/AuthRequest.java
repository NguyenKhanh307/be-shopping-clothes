package com.khanhdev.be_shopping_clothes.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}