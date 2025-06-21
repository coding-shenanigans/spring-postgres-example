package com.codingshenanigans.spring_postgres_example.repositories;

import com.codingshenanigans.spring_postgres_example.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
}
