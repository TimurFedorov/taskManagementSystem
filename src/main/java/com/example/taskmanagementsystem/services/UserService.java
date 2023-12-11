package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.models.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityException("Username: " + username + " not found"));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException("User id: " + id + " not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
