package com.codingshenanigans.spring_postgres_example.services;

import com.codingshenanigans.spring_postgres_example.models.Todo;
import com.codingshenanigans.spring_postgres_example.repositories.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Iterable<Todo> getAll() {
        return todoRepository.findAll();
    }
}
