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
     // READ ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ USER BY ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    // CREATE
    public User createUser(User user) {
        return userRepository.save(user);
    }
    // // UPDATE USER BY ID
    // public Optional<User> updateUser(Integer id, User userDetails) {
    //     return userRepository.findById(id).map(existingUser -> {
    //         existingUser.setName(userDetails.getName());
    //         return userRepository.save(existingUser);
    //     });
    // }
    // UPDATE USER BY ID
    public Optional<User> updateUser(Integer id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            if (userDetails.getName() != null) {
                existingUser.setName(userDetails.getName());
            }
            return userRepository.save(existingUser);
    });
    }
    // DELETE USER BY ID
    public boolean deleteUser(Integer id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
