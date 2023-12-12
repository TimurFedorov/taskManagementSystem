package com.example.taskmanagementsystem.mappers;

import com.example.taskmanagementsystem.dto.UserDTO;
import com.example.taskmanagementsystem.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUser(UserDTO userDTO);
}
