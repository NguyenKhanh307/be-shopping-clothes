package com.khanhdev.be_shopping_clothes.controller;


import com.khanhdev.be_shopping_clothes.dto.AuthRequest;
import com.khanhdev.be_shopping_clothes.dto.RegisterRequest;
import com.khanhdev.be_shopping_clothes.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/forgot")
    public String forgot(@RequestParam String email) {
        return authService.forgotPassword(email);
    }
}