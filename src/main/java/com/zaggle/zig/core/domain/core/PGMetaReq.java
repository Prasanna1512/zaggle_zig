package com.zaggle.zig.core.domain.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PGMetaReq {
    private int appId;
    private String pgSecret;
    private String pgKey;
}
