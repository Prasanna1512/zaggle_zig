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
public class AppWebhookId implements Serializable {
    @Column(name="webhook_id")
    private UUID webhookId;
    @Column(name="app_id")
    private UUID appId;
}
