package com.codingshenanigans.spring_postgres_example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Table("todos")
public record Todo(
    @Id
    Long id,
    @Column("title")
    String title,
    @Column("done")
    Boolean done,
    @Column("created_at")
    OffsetDateTime createdAt,
    @Column("updated_at")
    OffsetDateTime updatedAt
) {
}
