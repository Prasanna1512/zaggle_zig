package com.zaggle.zig.travel.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.zaggle.zig.travel.common.dto.error.ValidationError;
import com.zaggle.zig.travel.common.dto.meta.ErrorMeta;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private ErrorMeta meta;
    private List<ValidationError> data;
    private List<Error> errors;
    private String message;
}
