package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.models.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.security.JwtUser;
import com.example.taskmanagementsystem.services.UserService;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityException("Username: " + username + " not found"));
    }
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityException("User id: " + id + " not found"));
    }
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public String getJwtUserName () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser.getUsername();
    }
}
