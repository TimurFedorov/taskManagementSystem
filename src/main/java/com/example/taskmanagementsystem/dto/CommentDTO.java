package com.example.taskmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentDTO {

    @NotNull
    @Size(min = 2, max = 100)
    private String text;
    @NotNull
    private String taskHeader;

}
