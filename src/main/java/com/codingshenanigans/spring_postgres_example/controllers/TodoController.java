package com.codingshenanigans.spring_postgres_example.controllers;

import com.codingshenanigans.spring_postgres_example.models.Todo;
import com.codingshenanigans.spring_postgres_example.services.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Iterable<Todo> getAll() {
        return todoService.getAll();
    }
}
