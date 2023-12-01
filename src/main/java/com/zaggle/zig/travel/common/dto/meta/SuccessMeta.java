package com.zaggle.zig.travel.common.dto.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessMeta implements Serializable {
    private String requestId;
    private String timestamp;
    private Pagination pagination;
}
