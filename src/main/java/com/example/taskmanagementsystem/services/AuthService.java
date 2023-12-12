package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.UserDTO;

import java.util.Map;


public interface AuthService {
    Map<String, String> register(UserDTO userDTO);
    Map<String, String> login(UserDTO userDTO);

}
