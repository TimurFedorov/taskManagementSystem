package com.example.taskmanagementsystem.services.impl;

import com.example.taskmanagementsystem.dto.CommentDTO;
import com.example.taskmanagementsystem.mappers.CommentMapper;
import com.example.taskmanagementsystem.models.Comment;
import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.repositories.CommentRepository;
import com.example.taskmanagementsystem.security.JwtUser;
import com.example.taskmanagementsystem.services.CommentService;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskServiceImpl taskService;

    private final UserServiceImpl userService;
    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
    @Override
    public Comment save(CommentDTO commentDTO, Long taskId) {
        Task task = taskService.findById(taskId);
        Comment comment = commentMapper.commentDTOToComment(commentDTO);
        comment.setAuthor(userService.findUserByName(userService.getJwtUserName()));
        comment.setTask(task);
        return commentRepository.save(comment);
    }
    @Override
    public void deleteById(Long commentId) {
        Comment comment = findById(commentId);
        if (!userService.getJwtUserName().equals(comment.getAuthor().getUsername())) {
            throw new EntityException("No access - you are not author of comment");
        }
        commentRepository.delete(comment);
    }
    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityException("Comment id: " + id + " not found"));
    }
}
