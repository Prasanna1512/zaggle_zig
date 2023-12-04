package com.zaggle.zig.core.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppWebhookReq {
    private UUID webhookId;
    private String[] webhookIds;
    private long appId;
}
