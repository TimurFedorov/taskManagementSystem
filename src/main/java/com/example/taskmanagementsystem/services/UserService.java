package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.models.User;

import java.util.List;


public interface UserService {

    User findUserByName(String username);
    User findUserById(Long id);
    List<User> getAll();
    String getJwtUserName ();
}
