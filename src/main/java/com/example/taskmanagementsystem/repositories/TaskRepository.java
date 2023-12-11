package com.example.taskmanagementsystem.repositories;

import com.example.taskmanagementsystem.models.Task;
import com.example.taskmanagementsystem.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByHeader (String header);
    Optional<Task> findById (Long id);

    List<Task> findAllByAuthorId (Long authorId, Pageable pageable);
    List<Task> findAllByExecutorId (Long executorId, Pageable pageable);
}
