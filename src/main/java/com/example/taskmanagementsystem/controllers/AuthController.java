package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dto.AuthDTO;
import com.example.taskmanagementsystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid AuthDTO authDTO) {
        return authService.register(authDTO);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthDTO authDTO) {
        return authService.login(authDTO);
    }
}
