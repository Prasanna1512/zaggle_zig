package com.zaggle.zig.core.domain.core;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIReq {
    private UUID id;
    private String name;
    private String description;
    private int status;
    private String domain;
    private String uri;
    private String method;
    private JsonNode headers;
}
