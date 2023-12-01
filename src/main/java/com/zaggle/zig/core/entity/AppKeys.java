package com.zaggle.zig.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "keyName", "keyValue"}) })
public class AppKeys {
    @Id
    private UUID id;
    private String keyName;
    private String keyValue;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Column(columnDefinition = "integer default 1")
    private int status;

    @JsonIgnoreProperties("appKeys")
    @OneToOne( mappedBy = "appKeys", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private AppAppKeys appAppKeys;
}
