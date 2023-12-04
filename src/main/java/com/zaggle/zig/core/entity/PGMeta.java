package com.zaggle.zig.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PGMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int appId;
    private String pgSecret;
    private String pgKey;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Column(columnDefinition = "integer default 1")
    private int status;
}
