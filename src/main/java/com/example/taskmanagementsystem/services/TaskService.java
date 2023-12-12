package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.TaskDTO;
import com.example.taskmanagementsystem.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TaskService {
    Page<Task> getAllTasks(Pageable pageable);
    List<Task> getTasksByAuthor(Long authorId, Pageable pageable);
    List<Task> getTasksByExecutor(Long executorId, Pageable pageable);
    Task save(TaskDTO taskDTO);
    Task findById(Long taskId);
    Task update(TaskDTO newTaskDTO, Long id);
    Task updateStatus(String status, Long id);
    void deleteById(Long id);
}
