package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.AuthDTO;

import java.util.Map;


public interface AuthService {
    Map<String, String> register(AuthDTO authDTO);
    Map<String, String> login(AuthDTO authDTO);

}
