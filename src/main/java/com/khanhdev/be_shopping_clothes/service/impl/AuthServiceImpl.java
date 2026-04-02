package com.khanhdev.be_shopping_clothes.service.impl;


import com.khanhdev.be_shopping_clothes.dto.AuthRequest;
import com.khanhdev.be_shopping_clothes.dto.RegisterRequest;
import com.khanhdev.be_shopping_clothes.entity.User;
import com.khanhdev.be_shopping_clothes.repository.UserRepository;
import com.khanhdev.be_shopping_clothes.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());

        userRepository.save(user);
        return "Register success!";
    }

    @Override
    public String login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return "Wrong password";
        }

        return "Login success!";
    }

    @Override
    public String forgotPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) return "Email not found";

        // Demo: reset về 123456
        User user = userOpt.get();
        user.setPassword(encoder.encode("123456"));
        userRepository.save(user);

        return "Password reset to 123456";
    }
}