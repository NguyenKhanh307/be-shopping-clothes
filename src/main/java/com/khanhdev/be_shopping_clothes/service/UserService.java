package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.entity.User;
import com.khanhdev.be_shopping_clothes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            if (userDetails.getFullName() != null) {
                existingUser.setFullName(userDetails.getFullName());
            }
            return userRepository.save(existingUser);
        });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
           userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
