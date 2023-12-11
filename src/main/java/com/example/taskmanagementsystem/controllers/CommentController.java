package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.dto.CommentDTO;
import com.example.taskmanagementsystem.dto.TaskDTO;
import com.example.taskmanagementsystem.models.Comment;
import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.services.CommentService;
import com.example.taskmanagementsystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @GetMapping()
    @Operation(summary = "Get all comments information")
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @PostMapping("/{taskId}")
    @Operation (summary = "Add new comment")
    public Comment add(@RequestBody CommentDTO commentDTO,
                       @PathVariable("taskId") Long taskId) {
        return commentService.save(commentDTO, taskId);
    }

    @DeleteMapping("/{commentId}")
    @Operation (summary = "Delete comment if you are author")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.deleteById(commentId);
    }


}
