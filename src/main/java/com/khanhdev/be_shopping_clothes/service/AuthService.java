package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.dto.AuthRequest;
import com.khanhdev.be_shopping_clothes.dto.RegisterRequest;


public interface AuthService {
    String register(RegisterRequest request);
    String login(AuthRequest request);
    String forgotPassword(String email);
}