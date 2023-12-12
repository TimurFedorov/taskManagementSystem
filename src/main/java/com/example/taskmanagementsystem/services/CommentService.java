package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.CommentDTO;
import com.example.taskmanagementsystem.models.Comment;

import java.util.List;


public interface CommentService {

    List<Comment> getAll();
    Comment save(CommentDTO commentDTO, Long taskId);
    void deleteById(Long commentId);
    Comment findById(Long id);
}
