package com.khanhdev.be_shopping_clothes.controller;

import com.khanhdev.be_shopping_clothes.dto.AuthResponse;
import com.khanhdev.be_shopping_clothes.dto.LoginRequest;
import com.khanhdev.be_shopping_clothes.dto.RegisterRequest;
import com.khanhdev.be_shopping_clothes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/register
     * Body: { full_name, email, password, phone }
     * Response: { message, user: { id, full_name, email, role, avatar_url, phone } }
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Validate cơ bản
            if (request.getFull_name() == null || request.getFull_name().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Họ và tên không được để trống."));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Email không được để trống."));
            }
            if (request.getPassword() == null || request.getPassword().length() < 6) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Mật khẩu phải có ít nhất 6 ký tự."));
            }

            AuthResponse response = authService.register(request);
            return ResponseEntity.status(201).body(response);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("message", e.getReason()));
        }
    }

    /**
     * POST /api/auth/login
     * Body: { email, password }
     * Response: { message, user: { id, full_name, email, role, avatar_url, phone } }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Email không được để trống."));
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Mật khẩu không được để trống."));
            }

            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("message", e.getReason()));
        }
    }
}
