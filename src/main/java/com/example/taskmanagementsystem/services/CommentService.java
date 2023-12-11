package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.dto.CommentDTO;
import com.example.taskmanagementsystem.mappers.CommentMapper;
import com.example.taskmanagementsystem.models.Comment;
import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.repositories.CommentRepository;
import com.example.taskmanagementsystem.security.JwtUser;
import com.example.taskmanagementsystem.utils.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TaskService taskService;

    private final UserService userService;

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment save(CommentDTO commentDTO, Long taskId) {
        Task task = taskService.findById(taskId);
        Comment comment = commentMapper.commentDTOToComment(commentDTO);
        comment.setAuthor(userService.findUserByName(getJwtUserName()));
        comment.setTask(task);
        return commentRepository.save(comment);
    }

    public void deleteById(Long commentId) {
        Comment comment = findById(commentId);
        if (!getJwtUserName().equals(comment.getAuthor().getUsername())) {
            throw new EntityException("No access - you are not author of task");
        }
        commentRepository.delete(comment);
    }

    private String getJwtUserName () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser.getUsername();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityException("Comment id: " + id + " not found"));
    }
}
