package com.example.taskmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TaskDTO {
    @NotNull
    @Size(min = 4, max = 50)
    private String header;

    private String description;
    @NotNull
    private String status;
    @NotNull
    private String priority;
    @NotNull
    private String executorName;
}
