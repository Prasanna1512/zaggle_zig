package com.zaggle.zig.core.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAppApiReq {
    private UUID clientId;
    private UUID appId;
    private String apiId;
}
