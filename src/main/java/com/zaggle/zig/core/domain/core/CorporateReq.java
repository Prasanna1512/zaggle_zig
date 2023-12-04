package com.zaggle.zig.core.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateReq {
    private UUID id;
    private String name;
    private String description;
    private int status;
}
