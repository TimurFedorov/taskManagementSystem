package com.example.taskmanagementsystem.mappers;

import com.example.taskmanagementsystem.dto.AuthDTO;
import com.example.taskmanagementsystem.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User authDTOToUser (AuthDTO authDTO);
}
