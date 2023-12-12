package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    public void IncorrectLoginTest() throws Exception {
        long size = userRepository.count();
        Map<String,String> request = new HashMap<>()
        {{
            put("username", "test@test.ru");
            put("password", "incorrect password");
        }};
        this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void RegistrationAndAuthorizationTest() throws Exception {
        long size = userRepository.count();
        Map<String,String> request = new HashMap<>()
        {{
            put("username", "test@test.ru");
            put("password", "1234512345");
        }};
        this.mockMvc.perform(post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
        assertThat(userRepository.count() == size + 1);

        this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(jsonPath("$.jwt-token").exists())
                .andExpect(status().isOk());

    }
}
