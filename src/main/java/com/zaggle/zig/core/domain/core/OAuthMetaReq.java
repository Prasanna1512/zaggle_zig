package com.zaggle.zig.core.domain.core;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthMetaReq {
    private UUID id;
    private JsonNode callBackParams;
    private JsonNode tokenParams;
    private JsonNode refreshTokenParams;
    private UUID appId;
    private int status;
}
