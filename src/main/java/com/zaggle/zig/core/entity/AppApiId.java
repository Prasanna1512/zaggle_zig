package com.zaggle.zig.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Embeddable
public class AppApiId implements Serializable {
    @Column(name = "api_id")
    private UUID apiId;
    @Column(name = "app_id")
    private Long appId;
}
