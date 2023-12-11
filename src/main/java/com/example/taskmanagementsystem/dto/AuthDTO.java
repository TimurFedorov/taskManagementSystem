package com.example.taskmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthDTO {
    @Email
    @NotNull
    private String username;
    @NotNull
    private String password;
}
