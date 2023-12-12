package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.dto.UserDTO;
import com.example.taskmanagementsystem.mappers.UserMapper;
import com.example.taskmanagementsystem.models.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.security.JWTUtil;
import com.example.taskmanagementsystem.services.AuthService;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> register(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new EntityException("User with username: " + user.getUsername() + " is already registered");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return Map.of("jwt-token", token);
    }

    @Override
    public Map<String, String> login(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                        userDTO.getPassword());
        authenticationManager.authenticate(authInputToken);
        String token = jwtUtil.generateToken(userDTO.getUsername());
        return Map.of("jwt-token", token);
    }

}
