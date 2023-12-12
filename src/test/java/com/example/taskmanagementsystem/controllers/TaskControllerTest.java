package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers

@Sql(value = "/data/sql/add_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data/sql/add_tasks.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data/sql/add_comments.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/data/sql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void getAllTasksTest() throws Exception{
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()")
                        .value(taskRepository.count()));
    }

    @Test
    public void getTasksByAuthor() throws Exception{
        this.mockMvc.perform(get("/api/tasks/author/{authorId}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(taskRepository.findAllByAuthorId(1L, Pageable.unpaged()).size()));
    }

    @Test
    public void getTasksByExecutor() throws Exception{
        this.mockMvc.perform(get("/api/tasks/executor/{executorId}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(taskRepository.findAllByExecutorId(1L, Pageable.unpaged()).size()));
    }

    @Test
    public void addIncorrectTask() throws Exception {
        Map<String,String> request = new HashMap<>()
        {{
            put("description", "description");
            put("status", "PROCESS");
            put("priority", "LOW");
            put("executorName", "t@ya.ru");
        }};
        this.mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
