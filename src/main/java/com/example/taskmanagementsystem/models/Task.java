package com.example.taskmanagementsystem.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "header", unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "task_status")
    @NotNull
    private String status;

    @Column(name = "status")
    @NotNull
    private String priority;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @NotNull
    private User author;

    @ManyToOne
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    @NotNull
    private User executor;

    @OneToMany (mappedBy = "task")
    private List<Comment> comments;

}
