package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.TaskDTO;
import com.example.taskmanagementsystem.mappers.TaskMapper;
import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.security.JwtUser;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public List<Task> getAllTasks(Pageable pageable) {
        return (List<Task>) taskRepository.findAll(pageable);
    }

    public List<Task> getTasksByAuthor(Long authorId, Pageable pageable) {
        return taskRepository.findAllByAuthorId(authorId, pageable);
    }

    public List<Task> getTasksByExecutor(Long executorId, Pageable pageable) {
        return  taskRepository.findAllByExecutorId(executorId, pageable);
    }

    public Task save(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        if (!taskRepository.findByHeader(task.getHeader()).isEmpty()) {
            throw new EntityException("Task with header: " + task.getHeader() + " is already exist");
        }
        task.setExecutor(userService.findUserByName(taskDTO.getExecutorName()));
        task.setAuthor(userService.findUserByName(getJwtUserName()));

        return taskRepository.save(task);
    }

    public Task findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityException("Task id: " + taskId + " not found"));
    }

    public Task update(TaskDTO newTaskDTO, Long id) {
        Task task = findById(id);
        if (!getJwtUserName().equals(task.getAuthor().getUsername())) {
            throw new EntityException("No access - you are not author of task");
        }
        task.setHeader(newTaskDTO.getHeader());
        task.setDescription(newTaskDTO.getDescription());
        task.setStatus(newTaskDTO.getStatus());
        task.setPriority(newTaskDTO.getPriority());
        task.setExecutor(userService.findUserByName(newTaskDTO.getExecutorName()));
        return taskRepository.save(task);
    }

    public Task updateStatus(String status, Long id) {
        Task task = findById(id);
        if (!(getJwtUserName().equals(task.getExecutor().getUsername())
                || getJwtUserName().equals(task.getAuthor().getUsername()))) {
            throw new EntityException("No access - you are not executor or author of task");
        }
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        Task task = findById(id);
        if (!getJwtUserName().equals(task.getAuthor().getUsername())) {
            throw new EntityException("No access - you are not author of task");
        }
        taskRepository.delete(task);
    }

    private String getJwtUserName () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser.getUsername();
    }
}
