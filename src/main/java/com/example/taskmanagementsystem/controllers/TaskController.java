package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dto.TaskDTO;
import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    @Operation (summary = "Get all task information")
    public List<Task> getAll(@ParameterObject Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @PostMapping()
    @Operation (summary = "Add new task")
    public Task add(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.save(taskDTO);
    }

    @GetMapping("/{taskId}")
    @Operation (summary = "Find task by id")
    public Task getById(@PathVariable("taskId") Long taskId) {
        return taskService.findById(taskId);
    }

    @PutMapping("/{taskId}")
    @Operation (summary = "Update task if you are author")
    public Task update(@RequestBody @Valid TaskDTO newTask,
                       @PathVariable("taskId") Long taskId) {
        return taskService.update(newTask, taskId);
    }

    @PutMapping("/{taskId}/{newStatus}")
    @Operation (summary = "Update task status if you are author or executor")
    public Task updateStatus(@PathVariable("taskId") Long taskId,
                             @PathVariable("newStatus") String newStatus) {
        return taskService.updateStatus(newStatus, taskId);
    }

    @DeleteMapping("/{taskId}")
    @Operation (summary = "Delete task if you are author")
    public void delete(@PathVariable("taskId") Long taskId) {
        taskService.deleteById(taskId);
    }

    @GetMapping("/author/{authorId}")
    @Operation (summary = "Get tasks by author")
    public List<Task> getTasksByAuthor(@PathVariable("authorId") Long authorId,
                             @ParameterObject Pageable pageable) {
        return taskService.getTasksByAuthor(authorId, pageable);
    }

    @GetMapping("/executor/{executorId}")
    @Operation (summary = "Get tasks by executor")
    public List<Task> getTasksByExecutor(@PathVariable("executorId") Long executorId,
                                       @ParameterObject Pageable pageable) {
        return taskService.getTasksByExecutor(executorId ,pageable);
    }

}
