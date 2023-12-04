package com.zaggle.zig.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppAuthAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "serial")
    private UUID id;
    private UUID appId;
    private String name;
    private String realmId;
    private String authCode;
    private String accessToken;
    private String refreshToken;
    @Column(columnDefinition = "integer default 1")
    private int status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
