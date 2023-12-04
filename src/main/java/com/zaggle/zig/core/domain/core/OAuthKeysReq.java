package com.zaggle.zig.core.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthKeysReq {
    private UUID id;
    private String clientId;
    private String clientSecret;
    private UUID corporateId;
    private UUID appId;
    private int status;
}
