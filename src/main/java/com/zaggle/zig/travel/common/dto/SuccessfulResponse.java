package com.zaggle.zig.travel.common.dto;


import com.zaggle.zig.travel.common.dto.meta.SuccessMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessfulResponse<T> implements Serializable {
    private SuccessMeta meta;
    private List<T> data;
    private Object errors;
    private String message;
}
