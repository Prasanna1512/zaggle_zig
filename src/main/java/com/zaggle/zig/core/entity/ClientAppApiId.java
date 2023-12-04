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
public class ClientAppApiId implements Serializable {
    @Column(name="client_id")
    private UUID clientId;
    @Column(name="app_id")
    private UUID appId;
    @Column(name="api_id")
    private UUID apiId;
}
