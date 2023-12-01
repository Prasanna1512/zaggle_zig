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
public class AppAppKeysId implements Serializable {
    @Column(name="appkeys_id")
    private UUID appKeysId;
    @Column(name="app_id")
    private Long appId;
}
