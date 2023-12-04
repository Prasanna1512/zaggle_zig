package com.zaggle.zig.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
public class Corporate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique=true)
    private String name;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
