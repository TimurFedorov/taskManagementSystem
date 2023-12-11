package com.example.taskmanagementsystem.mappers;

import com.example.taskmanagementsystem.dto.CommentDTO;
import com.example.taskmanagementsystem.models.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment commentDTOToComment (CommentDTO commentDTO);

}
