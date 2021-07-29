package com.codecool.todo.repository;

import com.codecool.todo.entity.Todo;
import com.codecool.todo.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> findAllByStatus(Status status);
    void deleteAllByStatus(Status status);
}
